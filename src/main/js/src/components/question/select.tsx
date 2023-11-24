import { useState, useEffect, useMemo } from 'react'
import { FiCheck } from 'react-icons/fi'

import Reveal from '../reveal'
import Action from '../action'
import Error from './error'
import { Question } from '.'

interface Helper {
  value: string | null, visible?: boolean
}

interface Option {
  value: string,
  exclusive?: boolean
}

interface SelectQuestion extends Question {
  answer?: string[] | string,
  setAnswer: (val: string[]) => void,
  helper?: Helper,
  setHelper: (val: Helper) => void,
  multiple?: boolean,
  options?: Option[],
  letter?: number
}

export default function Select( props: SelectQuestion ) {

  const [willSkip, setWillSkip] = useState(false)

  const largestOption = useMemo(() => {
    const copy = props.options ? [...props.options] : []
    return copy.sort((a,b) =>b.value?.length - a.value?.length)[0].value.length
  }, [props.options])

  useEffect(() => {
    // between A and Z
    if(props.letter && props.current === props.id && props.letter >= 65 && (props.letter - 65) < (props.options ?? []).length) {
      handleClick(props.options ? props.options[props.letter - 65] : null)
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

  const handleClick = (option: Option | null) => {
    let updated = props.answer ? [...props.answer] : []

    if(!option) {
      return
    }

    // avoid multiple select if needed
    if(!props.multiple || option.exclusive) {
      updated = []
    }

    // if already selected
    if(props.answer && props.answer.includes(option.value)) {
      setAnswer(updated.filter(e => e !== option.value))
    } else {
        // remove exclusive answers
        updated = updated.filter(answer => !(props.options ?? []).find(opt => opt.exclusive && opt.value === answer))

      updated.push(option.value)
      setAnswer(updated)

      if(!props.multiple || option.exclusive) {
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
    <section className='hero min-h-screen p-1 pb-12' id={`q${props.id}`}>
      <Reveal duration={.2} className='hero-content text-center w-full max-w-xl'>
        <div className='text-left flex flex-col w-full'>
          <h1 className='text-lg font-bold'>{props.main}</h1>
          <p className='pt-2 pb-4 text-sm'>{props.desc}</p>
          <article className={`grid w-full grid-cols-[repeat(auto-fill,minmax(${largestOption > 20 ? '300' : '200'}px,_1fr))] gap-3 mb-6`}>
            {
              (props.options ?? []).map((option, index) => (
                <div
                  className={`
                    relative cursor-pointer border-neutral px-10 flex-1 py-2 flex justify-center items-center border rounded-lg
                    ${(props.answer && props.answer.includes(option.value)) ? 'bg-neutral' : 'bg-accent'}
                    ${(props.answer && props.answer.includes(option.value) && willSkip) ? 'animate-pulse-quick' : ''}
                  `}
                  key={index}
                  onClick={handleClick.bind(null, option)}
                >
                  <div
                    className={`
                      border border-neutral text-[7px] left-3 absolute rounded-sm w-4 h-4 mr-2 flex justify-center items-center
                      ${(props.answer && props.answer.includes(option.value)) ? 'bg-neutral text-accent border-accent' : 'border-neutral'}
                    `}
                  >
                    {(index + 10).toString(36).toUpperCase()}
                  </div>
                  <p className={`whitespace-nowrap ${(props.answer && props.answer.includes(option.value)) ? 'text-accent' : 'text-neutral'}`} >{option.value}</p>
                  {
                    (props.answer && props.answer.includes(option.value)) &&
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