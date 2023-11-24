import { useState, useMemo, useRef, useEffect } from 'react'
import MobileDetect from 'mobile-detect';

import Question from '../components/question';
import type { Question as QuestionProps, Helper } from '../components/question';
import { useControls } from '../hooks/controls';

interface Survey {
	questions: QuestionProps[],
	mode: string,
	id: string,
	title: string,
	about: string,
  duration?: number
}


export default function Home( ) {

  const isAndroid = useMemo(() => {
    const md = new MobileDetect(window.navigator.userAgent);
    return md.os() === "AndroidOS";
  }, []);

	const [survey, setSurvey] = useState<null | Survey>(null)
  useEffect(() => {
    setSurvey(STATIC);
  }, [])
	const [hidden, setHidden] = useState<{[key: string|number]: boolean}>({})

	interface Answers {[key: string|number]: (string | string[])}
	const [_answers, _setAnswers] = useState<Answers>({})
  const answers = useRef<Answers>({})

  interface Helpers {[key: string|number] : Helper}
  const helpers = useRef<Helpers>({})
  const setHelpers = (val: Helpers) => {
    helpers.current = val
  }

  const [letter, setPressedLetter] = useState<number|null>()

  const question = useRef<number | null>(null)
  const moving = useRef<string | null>(null)

  const move = async ( target: string ) => {
    if(moving.current === target || question.current === ((survey?.questions.length || 0) - 1)) {
      return
    }

    const q = survey?.questions[question.current || 0]
    const h = helpers.current[question.current || 0]

    // is field invalid
    if(target === 'next' && q?.required && h.value) {
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
    while(!node && id < (survey?.questions.length || 0)) {
      id = target === 'next' ? id + 1 : id - 1
      node = document.getElementById(`q${id}`)
    }
    if(!node) {
      return
    }

    updateHidden(id)

    // store answers
    // if(survey?.mode === 'live' && target === 'next' && Object.keys(answers.current).length) {
    //   if(id === (survey.questions.length - 1)) {
    //     storeAnswers(survey, answers.current, Date.now() - timer.current )
    //   } else {
    //     storeAnswers(survey, answers.current)
    //   }
    // }

    if((question.current || 0) > -1) {
      // untarget input if any
      const input = document.getElementById(`q${question.current}-focus`)
      if(input) {
        input.blur()
        if(isAndroid) {
          // let time to close keyboard
          await new Promise(resolve => setTimeout(resolve, 300))
        }
      }
    }

    question.current = id
    node.scrollIntoView()
    moving.current = target
    
    return setTimeout(() => {
      moving.current = null
      // target input if any
      const input = document.getElementById(`q${id}-focus`)
      if(input) {
        if(!isAndroid) {
          input.focus({ preventScroll: true })
        }
        input.onblur = () => {
          node = document.getElementById(`q${id}`)
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

	const updateHidden = async ( id: number ) => {
		if(!survey) {
			return;
		}

    const dependent = Object.values(survey.questions).filter(q => q.condition && q.condition.some( cond => cond.question === id))
    const updated = hidden
    dependent.forEach(q => {
      if(q.condition && q.condition.every(cond => {
        if(cond.includes) { 
          return answers.current && answers.current[cond.question] && answers.current[cond.question].includes(cond.includes)
        } else if (cond.answered) {
          return answers.current && answers.current[cond.question]
        } else if(cond.answered === false) {
          return answers.current && !answers.current[cond.question]
        } else {
          return false
        }
      })) {
        updated[q.id] = false
      } else {
        updated[q.id] = true
      }
    })
    setHidden({...updated})
  }

  const main = useControls( move, setPressedLetter )

	return (
		<div ref={main as React.RefObject<HTMLDivElement>} data-theme="light" className="w-screen h-screen p-2 lg:p-6 overflow-y-auto scrollbar-hide scroll-smooth">
			<main className="flex flex-col justify-center items-center">
        {
          survey && survey.questions.map((q, id) => {
              if(q.condition && (hidden[id] || ( q.condition.every(cond => cond.answered !== false)))) {
                return
              }

              return (<Question
                {...q}
                key={q.id} id={q.id} move={move}
                current={question.current}
                duration={id===0 ? survey.duration : null}
                letter={typeof letter === 'number' ? letter : undefined}
                answer={_answers[id]}
                setAnswer={(val : Answers) => {
                  const updatedAnswers = {..._answers, ...val}
                  // if(props.survey.mode === 'live') {
                  //   manageUploads(updatedAnswers)
                  // }
                  _setAnswers(updatedAnswers)
                  answers.current = updatedAnswers
                  updateHidden(id)
                }} 
                helper={helpers.current[id]}
                setHelpers={(val) => setHelpers({...helpers.current, ...val})}
              />)}
          )
        }
			</main>
		</div>
	);
}

// Static example of a survey json object
const STATIC = {
  questions: [
    {
      id: 0,
      type: 'statement',
      main: 'This is a statement. Be sure your input here is greatly valued :)',
      about: 'You\'ll find a special gift in your inbox as soon as completed!'
    },
    {
      id: 1,
      required: true,
      type: 'text',
      main: 'How should we call you?',
      about: 'No need for something too formal here.'
    },
    {
      id: 2,
      type: 'select',
      multiple: false,
      required: true,
      options: [{value: 'YES'}, {value: 'OF COURSE'}],
      main: 'Are you comfortably seated?',
      about: 'Getting a nice drink is highly recommended...'
    },
    {
      id: 3,
      main: 'What\' your email?',
      about: 'So we can keep you posted',
      type: 'email',
      required: true
    },
    {
      id: 4,
      type: 'select',
      main: 'What\'s your favorite meal?',
      about: 'We just would like to know how to please you :)',
      options: [{value:'Pastas'}, {value:'Pizza'}, {value:'Fish & Chips'}, {value:'Burger'}]
    },
    {
      id: 5,
      type: 'final',
      confetti: true,
      main: 'Thank you SO much for your time!',
      about: 'Your input here helped a lot more than you can imagine.'
    }
  ],
  mode: 'test',
  id: '1234',
  title: 'Test survey',
  about: 'Hello, this is a test survey'
}