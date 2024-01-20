import { useState, useMemo, useRef, useEffect } from "react";
import MobileDetect from "mobile-detect";
import { useParams } from "react-router-dom";

import Question from "../components/question";
import type { Question as QuestionProps, Helper } from "../components/question";
import { useControls } from "../hooks/controls";
import type { Schemas } from "../apiClient/client";
import { apiclient } from "../main";

export default function FormAnswer() {
	const isAndroid = useMemo(() => {
		const md = new MobileDetect(window.navigator.userAgent);
		return md.os() === "AndroidOS";
	}, []);

	const [survey, _setSurvey] = useState<null | Schemas.Prefab>(null);
	const _survey = useRef<null | Schemas.Prefab>(null);

	// Init a question array so its easier to browse
	interface Question extends Schemas.Field {
		id: string;
	}
	const [questions, _setQuestions] = useState<null | Question[]>(null);
	const _questions = useRef<null | Question[]>(null);

	const setSurvey = (value: Schemas.Prefab) => {
		_survey.current = value;
		_setSurvey(value);

		const array = value?.groups?.flatMap(
			(group) => group.fields?.map((field) => ({ ...field, group: group.label, id: `${group.name}.${field.name}` })),
		);
		array?.push({
			id: "thanks",
			label: "Merci pour vos réponses !",
			name: "thanks",
			caption: "thanks",
			group: "Remerciements",
			rules: {
				excludes: [],
				fieldType: "FINAL",
				hidden: false,
				optional: false,
				selectorValues: [],
				typeRules: [],
			},
		});
		_setQuestions(array as Question[]);
		_questions.current = array as Question[];
	};

	const { prefabName } = useParams();

	useEffect(() => {
		apiclient
			.get(`/api/prefabs/${prefabName}`)
			.then((res) => res.json())
			.then((res) => {
				console.log(res);
				setSurvey(res);
			})
			.catch(() => {});
	}, []);
	const [hidden, setHidden] = useState<Record<string | number, boolean>>({});

	type Answers = Record<string | number, string | string[]>;

	const [_answers, _setAnswers] = useState<Answers>({});
	const answers = useRef<Answers>({});

	interface Helpers {
		[key: string | number]: Helper;
	}
	const helpers = useRef<Helpers>({});
	const [_helpers, _setHelpers] = useState<Helpers>({});
	const setHelpers = (val: Helpers) => {
		helpers.current = val;
		_setHelpers(val);
	};

	const [letter, setPressedLetter] = useState<number | null>();

	const [_question, _setQuestion] = useState<number | null>(null);
	const question = useRef<number | null>(null);
	const moving = useRef<string | null>(null);

	const move = async (target: string) => {
		if (
			moving.current === target ||
			_questions.current === null ||
			question.current === (_questions.current?.length || 0) - 1
		) {
			return;
		}

		const q = _questions.current[question.current || 0];
		const h = helpers.current[_questions.current[question.current || 0].id];

		// is field invalid
		if (target === "next" && !q?.rules?.optional && h?.value) {
			const res = helpers.current;
			res[_questions.current[question.current || 0].id] = {
				...res[_questions.current[question.current || 0].id],
				visible: true,
			};
			setHelpers({ ...res });
			moving.current = target;
			return setTimeout(() => {
				moving.current = null;
			}, 1000);
		}

		// starts moving
		let id = question.current || 0;
		let node: HTMLElement | null = null;
		while (!node && id < (_questions.current.length || 0)) {
			id = target === "next" ? id + 1 : id - 1;
			node = document.getElementById(_questions.current[id].id);
		}
		if (!node) {
			return;
		}

		// updateHidden(id)

		// store answers
		if (Object.keys(answers.current).length && target === "next" && id === _questions.current?.length - 1) {
			const prepared = {
				_id: _survey.current?._id,
				prefabName,
				groups: _survey.current?.groups?.map((group) => ({
					name: group.name,
					fields: group.fields
						?.map((field) => {
							if (field.rules?.fieldType.toLowerCase() === "date") {
								return {
									name: field.name,
									value: Date.parse(answers.current[`${group.name}.${field.name}`]?.toString())
										.valueOf()
										.toString(),
								};
							} else {
								return {
									name: field.name,
									value: answers.current[`${group.name}.${field.name}`],
								};
							}
						})
						.filter((field) => field.value !== null && field.value !== undefined),
				})),
			};
			apiclient.post(`/api/formdata/${prefabName}`, prepared).then((res) => console.log(res));
		}

		if (question.current !== null && (question.current || 0) > -1) {
			// untarget input if any
			const input = document.getElementById(`${_questions.current[question.current].id}-focus`);
			if (input) {
				input.blur();
				if (isAndroid) {
					// let time to close keyboard
					await new Promise((resolve) => setTimeout(resolve, 300));
				}
			}
		}

		question.current = id;
		_setQuestion(id);
		node.scrollIntoView();
		moving.current = target;

		return setTimeout(() => {
			moving.current = null;
			// target input if any
			if (_questions.current === null) {
				return;
			}
			const input = document.getElementById(`${_questions.current[id].id}-focus`);
			if (input) {
				if (!isAndroid) {
					input.focus({ preventScroll: true });
				}
				input.onblur = () => {
					if (_questions.current === null) {
						return;
					}
					node = document.getElementById(_questions.current[id].id);
					if (node) {
						setTimeout(() => {
							if (!moving.current) {
								node?.scrollIntoView();
							}
						}, 250);
					}
				};
			}
		}, 1000);
	};

	// const updateHidden = async ( id: number ) => {
	// 	if(!survey) {
	// 		return;
	// 	}

	//   const dependent = Object.values(survey.questions).filter(q => q.condition && q.condition.some( cond => cond.question === id))
	//   const updated = hidden
	//   dependent.forEach(q => {
	//     if(q.condition && q.condition.every(cond => {
	//       if(cond.includes) {
	//         return answers.current && answers.current[cond.question] && answers.current[cond.question].includes(cond.includes)
	//       } else if (cond.answered) {
	//         return answers.current && answers.current[cond.question]
	//       } else if(cond.answered === false) {
	//         return answers.current && !answers.current[cond.question]
	//       } else {
	//         return false
	//       }
	//     })) {
	//       updated[q.id] = false
	//     } else {
	//       updated[q.id] = true
	//     }
	//   })
	//   setHidden({...updated})
	// }

	const main = useControls(move, setPressedLetter);

	return (
		<div
			ref={main as React.RefObject<HTMLDivElement>}
			data-theme="light"
			className="w-screen h-screen p-2 lg:p-6 overflow-y-auto scrollbar-hide scroll-smooth"
		>
			<main className="flex flex-col justify-center items-center">
				{survey &&
					questions?.map((q, id) => {
						// if(q.condition && (hidden[id] || ( q.condition.every(cond => cond.answered !== false)))) {
						//   return
						// }

						return (
							<Question
								{...q}
								key={id}
								n={id}
								id={q.id}
								move={move}
								current={_question}
								type={q.rules?.fieldType}
								required={!q.rules?.optional}
								letter={typeof letter === "number" ? letter : undefined}
								answer={_answers[q.id]}
								setAnswer={(val: Answers) => {
									const updatedAnswers = { ..._answers, ...val };
									// if(props.survey.mode === 'live') {
									//   manageUploads(updatedAnswers)
									// }
									_setAnswers(updatedAnswers);
									answers.current = updatedAnswers;
									// updateHidden(id)
								}}
								helper={_helpers[q.id]}
								setHelpers={(val) => setHelpers({ ...helpers.current, ...val })}
							/>
						);
					})}
			</main>
		</div>
	);
}
