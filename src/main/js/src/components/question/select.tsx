import { useState, useEffect, useMemo } from 'react'
import { FiCheck } from 'react-icons/fi'

import Reveal from '../reveal'
import Action from '../action'
import Error from './error'
import { Question } from '.'

interface Helper {
  value: string | null, visible?: boolean
}

interface TypeRule {
  value: string
}

interface SelectQuestion extends Question {
  answer?: string[] | string,
  setAnswer: (val: string[]) => void,
  helper?: Helper,
  setHelper: (val: Helper) => void,
  rules: {
    selectorValues: string[],
    typeRules: TypeRule[]
  },
  letter?: number
}

export default function Select( props: SelectQuestion ) {

  const [willSkip, setWillSkip] = useState(false)

  const largestOption = useMemo(() => {
    const copy = props.rules.selectorValues ? [...props.rules.selectorValues] : []
    return copy?.length ? copy.sort((a,b) =>b?.length - a?.length)[0].length : 0
  }, [props.rules.selectorValues])

  useEffect(() => {
    // between A and Z
    if(props.letter && props.current === props.n && props.letter >= 65 && (props.letter - 65) < (props.rules.selectorValues ?? []).length) {
      handleClick(props.rules.selectorValues ? props.rules.selectorValues[props.letter - 65] : null)
    }
  }, [props.letter])

  const updateHelper = (val?: string[] | null) => {
    if(!props.required) {
      props.setHelper({ value: null, visible: false })
    } else {
      const a = val !== undefined ? val : props.answer
      if(!a || !a.length) {
        props.setHelper({ value: 'Please make a selection' })
      } else {
        props.setHelper({ value: null, visible: false })
      }
    }
  }

  useEffect(updateHelper, [props.answer])

  const setAnswer = (val: string[]) => {
    props.setAnswer(val)
    updateHelper(val)
  }

  const handleClick = (option: string | null) => {
    let updated = props.answer ? [...props.answer] : []

    if(!option) {
      return
    }

    // avoid multiple select if needed
    if(!props.rules.typeRules.find(rule => rule.value === "MULTIPLE_CHOICE")) {
      updated = []
    }

    // if already selected
    if(props.answer && props.answer.includes(option)) {
      setAnswer(updated.filter(e => e !== option))
    } else {
        // // remove exclusive answers
        // updated = updated.filter(answer => !(props.rules.selectorValues ?? []).find(opt => opt.exclusive && opt.value === answer))

        updated.push(option)
        setAnswer(updated)

        if(!props.rules.typeRules.find(rule => rule.value === "MULTIPLE_CHOICE")) {
          nextWithDelay()
        }
    }
  }

  const nextWithDelay = () => {
    setWillSkip(true)
    setTimeout(() => {
      setWillSkip(false)
      next()
    }, 1000);
  }

  const next = () => {
    if(props.next) {
      props.next()
    }
  }

  return (
    <section className='hero min-h-screen p-1 pb-12' id={props.id}>
      <Reveal duration={.2} className='hero-content text-center w-full max-w-xl'>
        <div className='text-left flex flex-col w-full'>
          <p className='pt-1 pb-2 text-sm opacity-40'>{props.group}</p>
          <h1 className='text-lg font-bold'>{props.label}</h1>
          <p className='pt-1 pb-2 text-sm'></p>
          <article className={`grid w-full grid-cols-[repeat(auto-fill,minmax(${largestOption > 20 ? '300' : '200'}px,_1fr))] gap-3 mb-6`}>
            {
              (props.rules.selectorValues ?? []).map((option, index) => (
                <div
                  className={`
                    relative cursor-pointer border-neutral px-10 flex-1 py-2 flex justify-center items-center border rounded-lg
                    ${(props.answer && props.answer.includes(option)) ? 'bg-neutral' : 'bg-accent'}
                    ${(props.answer && props.answer.includes(option) && willSkip) ? 'animate-pulse-quick' : ''}
                  `}
                  key={index}
                  onClick={handleClick.bind(null, option)}
                >
                  <div
                    className={`
                      border border-neutral text-[7px] left-3 absolute rounded-sm w-4 h-4 mr-2 flex justify-center items-center
                      ${(props.answer && props.answer.includes(option)) ? 'bg-neutral text-accent border-accent' : 'border-neutral'}
                    `}
                  >
                    {(index + 10).toString(36).toUpperCase()}
                  </div>
                  <p className={`whitespace-nowrap ${(props.answer && props.answer.includes(option)) ? 'text-accent' : 'text-neutral'}`} >{option}</p>
                  {
                    (props.answer && props.answer.includes(option)) &&
                    <span className='pl-1.5 absolute right-3'>
                      <FiCheck size={14} className='stroke-accent' />
                    </span>
                  }
                </div>
              ))
            }
          </article>
          {
            (props.helper && props.helper.visible) ? 
            <Error text={props.helper.value} /> :
            <Action {...props} />
          }
          
        </div>
      </Reveal>
    </section>
  )
} 