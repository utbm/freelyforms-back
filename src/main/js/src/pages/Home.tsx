import { useState, useMemo, useRef, useEffect } from 'react'
import MobileDetect from 'mobile-detect';

import Question from '../components/question';
import type { Question as QuestionProps, Helper } from '../components/question';
import { useControls } from '../hooks/controls';
import type { Schemas } from '../apiClient/client';


export default function Home( ) {

  const isAndroid = useMemo(() => {
    const md = new MobileDetect(window.navigator.userAgent);
    return md.os() === "AndroidOS";
  }, []);

	const [survey, _setSurvey] = useState<null | Schemas.Prefab>(null)
  const _survey = useRef<null | Schemas.Prefab>(null)

  // Init a question array so its easier to browse
  interface Question extends Schemas.Field {
    id: string
  }
  const [questions, _setQuestions] = useState<null | Question[]>(null)
  const _questions = useRef<null | Question[]>(null)

  const setSurvey = (value: Schemas.Prefab) => {
    _survey.current = value;
    _setSurvey(value);
    
    const array = value?.groups?.flatMap(group => group.fields?.map(field => ({...field, id: `${group.name}.${field.name}`})));
    array?.push({ 
      id: "thanks",
      label: "Merci pour vos réponses !",
      name: "thanks",
      caption: "thanks",
      rules: {
        excludes: [],
        fieldType: "FINAL",
        hidden: false,
        optional: false,
        selectorValues: [],
        typeRules: []
      }
    })
    _setQuestions(array as Question[]);
    _questions.current = array as Question[];
  }

  useEffect(() => {
    setSurvey(PREFAB as Schemas.Prefab);
  }, [])
	const [hidden, setHidden] = useState<Record<string | number, boolean>>({})

	type Answers = Record<string | number, string | string[]>


	const [_answers, _setAnswers] = useState<Answers>({})
  const answers = useRef<Answers>({})

  interface Helpers {[key: string|number] : Helper}
  const helpers = useRef<Helpers>({})
  const [_helpers, _setHelpers] = useState<Helpers>({})
  const setHelpers = (val: Helpers) => {
    helpers.current = val
    _setHelpers(val)
  }

  const [letter, setPressedLetter] = useState<number|null>()

  const [_question, _setQuestion] = useState<number | null>(null)
  const question = useRef<number | null>(null)
  const moving = useRef<string | null>(null)

  const move = async ( target: string ) => {
    if(moving.current === target || _questions.current === null || question.current === ((_questions.current?.length || 0) - 1)) {
      return
    }

    const q = _questions.current[question.current || 0]
    const h = helpers.current[question.current || 0]

    // is field invalid
    if(target === 'next' && !q?.rules?.optional && h?.value) {
      const res = helpers.current
      res[question.current || 0] = { ...res[question.current || 0], visible: true }
      setHelpers({...res})
      moving.current = target
      return setTimeout(() => {
        moving.current = null
      }, 1000);
    }

    // starts moving
    let id = question.current || 0
    let node: (HTMLElement | null) = null
    while(!node && id < (_questions.current.length || 0)) {
      id = target === 'next' ? id + 1 : id - 1
      node = document.getElementById(_questions.current[id].id)
    }
    if(!node) {
      return
    }

    // updateHidden(id)

    // store answers
    // if(survey?.mode === 'live' && target === 'next' && Object.keys(answers.current).length) {
    //   if(id === (survey.questions.length - 1)) {
    //     storeAnswers(survey, answers.current, Date.now() - timer.current )
    //   } else {
    //     storeAnswers(survey, answers.current)
    //   }
    // }

    if(question.current !== null && (question.current || 0) > -1) {
      // untarget input if any
      const input = document.getElementById(`${_questions.current[question.current].id}-focus`)
      if(input) {
        input.blur()
        if(isAndroid) {
          // let time to close keyboard
          await new Promise(resolve => setTimeout(resolve, 300))
        }
      }
    }

    question.current = id
    _setQuestion(id)
    node.scrollIntoView()
    moving.current = target
    
    return setTimeout(() => {
      moving.current = null
      // target input if any
      if(_questions.current === null){
        return;
      }
      const input = document.getElementById(`${_questions.current[id].id}-focus`)
      if(input) {
        if(!isAndroid) {
          input.focus({ preventScroll: true })
        }
        input.onblur = () => {
          if(_questions.current === null){
            return;
          }
          node = document.getElementById(_questions.current[id].id)
          if(node) {
            setTimeout(() => {
              if(!moving.current) {
                node?.scrollIntoView()
              }
            }, 250)
          }
        }
      }
    }, 1000);
  } 

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

  const main = useControls( move, setPressedLetter )

	return (
		<div ref={main as React.RefObject<HTMLDivElement>} data-theme="light" className="w-screen h-screen p-2 lg:p-6 overflow-y-auto scrollbar-hide scroll-smooth">
			<main className="flex flex-col justify-center items-center">
        {
          survey && questions?.map((q, id) => {
              // if(q.condition && (hidden[id] || ( q.condition.every(cond => cond.answered !== false)))) {
              //   return
              // }

              return (<Question
                {...q}
                key={id} n={id} id={q.id} move={move}
                current={_question}
                type={q.rules?.fieldType}
                required={!q.rules?.optional}
                letter={typeof letter === 'number' ? letter : undefined}
                answer={_answers[q.id]}
                setAnswer={(val : Answers) => {
                  const updatedAnswers = {..._answers, ...val}
                  // if(props.survey.mode === 'live') {
                  //   manageUploads(updatedAnswers)
                  // }
                  _setAnswers(updatedAnswers)
                  answers.current = updatedAnswers
                  // updateHidden(id)
                }} 
                helper={_helpers[id]}
                setHelpers={(val) => setHelpers({...helpers.current, ...val})}
              />)}
          )
        }
			</main>
		</div>
	);
}

// const PREFAB = {
//   "_id": {
//       "timestamp": 1701823594,
//       "date": "2023-12-06T00:46:34.000+00:00"
//   },
//   "name": "people",
//   "label": "Gens",
//   "caption": "Personnes",
//   "groups": [
//       {
//           "name": "personFields",
//           "label": "Identité",
//           "caption": "",
//           "fields": [
//               {
//                   "name": "name",
//                   "label": "Nom",
//                   "caption": "",
//                   "rules": {
//                       "optional": false,
//                       "excludes": [],
//                       "typeRules": [
//                           {
//                               "associatedTypes": [
//                                   "INTEGER",
//                                   "FLOAT",
//                                   "STRING",
//                                   "DATE",
//                                   "DATETIME"
//                               ],
//                               "value": "50",
//                               "name": "MaximumRule"
//                           }
//                       ],
//                       "hidden": false,
//                       "selectorValues": [],
//                       "fieldType": "STRING"
//                   }
//               },
//               {
//                   "name": "dob",
//                   "label": "Date de naissance",
//                   "caption": "",
//                   "rules": {
//                       "optional": false,
//                       "excludes": [],
//                       "typeRules": [],
//                       "hidden": false,
//                       "selectorValues": [],
//                       "fieldType": "DATE"
//                   }
//               },
//               {
//                   "name": "street",
//                   "label": "Rue de domicile",
//                   "caption": "",
//                   "rules": {
//                       "optional": false,
//                       "excludes": [],
//                       "typeRules": [
//                           {
//                               "associatedTypes": [
//                                   "SELECTOR"
//                               ],
//                               "value": "streets.streetFields.streetName",
//                               "name": "SelectDataSet"
//                           }
//                       ],
//                       "hidden": false,
//                       "selectorValues": [],
//                       "fieldType": "SELECTOR"
//                   }
//               },
//               {
//                   "name": "phone",
//                   "label": "Téléphone",
//                   "caption": "0612345678",
//                   "rules": {
//                       "optional": false,
//                       "excludes": [],
//                       "typeRules": [],
//                       "hidden": false,
//                       "selectorValues": [],
//                       "fieldType": "STRING"
//                   }
//               },
//               {
//                   "name": "sex",
//                   "label": "Sexe",
//                   "caption": "fieldCaption",
//                   "rules": {
//                       "optional": false,
//                       "excludes": [],
//                       "typeRules": [
//                           {
//                               "associatedTypes": [
//                                   "SELECTOR",
//                                   "BOOLEAN",
//                                   "DATE",
//                                   "DATETIME",
//                                   "FLOAT"
//                               ],
//                               "value": "CHECKBOX",
//                               "name": "AlternativeDisplay"
//                           }
//                       ],
//                       "hidden": false,
//                       "selectorValues": [
//                           "Homme",
//                           "Femme"
//                       ],
//                       "fieldType": "STRING"
//                   }
//               }
//           ]
//       }
//   ]
// }

const PREFAB = {
  "_id": {
      "timestamp": 1702514689,
      "date": "2023-12-14T00:44:49.000+00:00"
  },
  "name": "cars",
  "label": "Formulaire sur les voitures",
  "caption": "",
  "groups": [
      {
          "name": "generalInformation",
          "label": "Informations générales sur la voiture",
          "caption": "caption",
          "fields": [
              {
                  "name": "brand",
                  "label": "Quel est la marque de la voiture?",
                  "caption": "Citroen",
                  "rules": {
                      "optional": false,
                      "excludes": [],
                      "typeRules": [
                          {
                              "associatedTypes": [
                                  "INTEGER",
                                  "FLOAT",
                                  "STRING",
                                  "DATE",
                                  "DATETIME"
                              ],
                              "value": "50",
                              "name": "MaximumRule"
                          }
                      ],
                      "hidden": false,
                      "selectorValues": [],
                      "fieldType": "STRING"
                  }
              },
              {
                  "name": "model",
                  "label": "Quel est le modèle de la voiture?",
                  "caption": "Xsara",
                  "rules": {
                      "optional": false,
                      "excludes": [],
                      "typeRules": [
                          {
                              "associatedTypes": [
                                  "INTEGER",
                                  "FLOAT",
                                  "STRING",
                                  "DATE",
                                  "DATETIME"
                              ],
                              "value": "50",
                              "name": "MaximumRule"
                          }
                      ],
                      "hidden": false,
                      "selectorValues": [],
                      "fieldType": "STRING"
                  }
              },
              {
                  "name": "year",
                  "label": "Quelle est la date de mise en circulation de la voiture?",
                  "caption": "12/06/2000",
                  "rules": {
                      "optional": false,
                      "excludes": [],
                      "typeRules": [
                          {
                              "associatedTypes": [
                                  "SELECTOR",
                                  "BOOLEAN",
                                  "DATE",
                                  "DATETIME",
                                  "FLOAT"
                              ],
                              "value": "CALENDAR",
                              "name": "AlternativeDisplay"
                          }
                      ],
                      "hidden": false,
                      "selectorValues": [],
                      "fieldType": "DATE"
                  }
              }
          ]
      },
      {
          "name": "technicalInformation",
          "label": "Informations techniques sur la voiture",
          "caption": "caption",
          "fields": [
              {
                  "name": "engine",
                  "label": "Quel est la motorisation de la voiture?",
                  "caption": "2.0 HDI",
                  "rules": {
                      "optional": false,
                      "excludes": [],
                      "typeRules": [
                          {
                              "associatedTypes": [
                                  "SELECTOR",
                                  "BOOLEAN",
                                  "DATE",
                                  "DATETIME",
                                  "FLOAT"
                              ],
                              "value": "DROPDOWN",
                              "name": "AlternativeDisplay"
                          }
                      ],
                      "hidden": false,
                      "selectorValues": [
                          "1.8",
                          "1.9",
                          "2.0",
                          "2.0 HDI",
                          "1.8",
                          "1.9",
                          "2.0",
                          "2.0 HDI"
                      ],
                      "fieldType": "SELECTOR"
                  }
              },
              {
                  "name": "options",
                  "label": "Quelles sont les options de la voiture?",
                  "caption": "fieldCaption",
                  "rules": {
                      "optional": false,
                      "excludes": [],
                      "typeRules": [
                          {
                              "associatedTypes": [
                                  "SELECTOR",
                                  "BOOLEAN",
                                  "DATE",
                                  "DATETIME",
                                  "FLOAT"
                              ],
                              "value": "MULTIPLE_CHOICE",
                              "name": "AlternativeDisplay"
                          }
                      ],
                      "hidden": false,
                      "selectorValues": [],
                      "fieldType": "SELECTOR"
                  }
              },
              {
                  "name": "color",
                  "label": "Quelle est la couleur de la voiture?",
                  "caption": "fieldCaption",
                  "rules": {
                      "optional": false,
                      "excludes": [],
                      "typeRules": [
                          {
                              "associatedTypes": [
                                  "SELECTOR",
                                  "BOOLEAN",
                                  "DATE",
                                  "DATETIME",
                                  "FLOAT"
                              ],
                              "value": "RADIO",
                              "name": "AlternativeDisplay"
                          }
                      ],
                      "hidden": false,
                      "selectorValues": [
                          "Noir",
                          "Blanc",
                          "Rouge",
                          "Bleu"
                      ],
                      "fieldType": "SELECTOR"
                  }
              }
          ]
      },
      {
          "name": "feedbackInformation",
          "label": "Feeback de l'utilisateur sur la voiture",
          "caption": "caption",
          "fields": [
              {
                  "name": "comment",
                  "label": "Quel commentaire pouvez vous faire sur cette voiture?",
                  "caption": "La Xsara elle est aberrante frérot.",
                  "rules": {
                      "optional": false,
                      "excludes": [],
                      "typeRules": [],
                      "hidden": false,
                      "selectorValues": [],
                      "fieldType": "STRING"
                  }
              },
              {
                  "name": "tripsNumber",
                  "label": "Combien de trajets (en moyenne) faites vous par semaine avec cette voiture?",
                  "caption": "12",
                  "rules": {
                      "optional": false,
                      "excludes": [],
                      "typeRules": [],
                      "hidden": false,
                      "selectorValues": [],
                      "fieldType": "INTEGER"
                  }
              },
              {
                  "name": "email",
                  "label": "Entrez votre adresse email pour tenter de remporter la magnifique Xsara RS",
                  "caption": "gmk@miteux.fr",
                  "rules": {
                      "optional": false,
                      "excludes": [],
                      "typeRules": [
                          {
                              "associatedTypes": [
                                  "STRING",
                                  "STRING"
                              ],
                              "value": "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$",
                              "name": "RegexMatch"
                          }
                      ],
                      "hidden": false,
                      "selectorValues": [],
                      "fieldType": "STRING"
                  }
              }
          ]
      }
  ]
}