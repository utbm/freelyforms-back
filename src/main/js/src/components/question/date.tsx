import TextareaAutosize from 'react-textarea-autosize'
import { useEffect, useMemo } from 'react'
import MobileDetect from 'mobile-detect'

import type { Question } from './index'
import Error from './error'
import Reveal from '../reveal'
import Action from '../action'

interface DateQuestion extends Question {
  answer?: string | string[],
  setAnswer: (val: string) => void,
  rules?: {
    optional?: boolean
  }
}

export default function DateComponent( props: DateQuestion ) {

  var isDate = function(date: string) {
    let answer = true;
    try {
      answer = ((new Date(date)).toString() !== "Invalid Date") && !isNaN((new Date(date))?.valueOf());
    } catch {
      answer = false;
    }
    return answer
  }
  
  const updateHelper = (val?: string | null) => {
    if(!props.setHelper) {
      return;
    }
    if(props.rules?.optional) {
      props.setHelper({ value: null, visible: false })
    } else {
      const a = val !== undefined ? val : props.answer

      if(!a || typeof a !== 'string' || !isDate(a)) {
        props.setHelper({ value: 'Please fill this in' })
      } else {
        props.setHelper({ value: null, visible: false })
      }
    }

  }

  useEffect(updateHelper, [props.answer])

  const isAndroid = useMemo(() => {
    const md = new MobileDetect(window.navigator.userAgent);
    return md.os() === "AndroidOS";
  }, []);

  const setAnswer = (val: string) => {
    props.setAnswer(val)
    updateHelper(val)
  }

  return (
    <section className='hero min-h-screen p-1 pb-12' id={props.id}>
      <Reveal duration={.2} className='hero-content text-center w-full max-w-lg'>
        <form className='text-left flex flex-col w-full' method='POST' action='javascript:void(0)'>
          <p className='pt-1 pb-2 text-sm opacity-40'>{props.group}</p>
          <h1 className='text-lg font-bold'>{props.label}</h1>
          <p className='pt-1 pb-2 text-sm'></p>
          <input
            type='date'
            name={`${props.id}-focus`}
            id={`${props.id}-focus`}
            placeholder={props.caption}
            className='input resize-none text-lg mb-6 py-2 bg-accent border-neutral whitespace-nowrap'
            onChange={e => setAnswer(e.target.value)}
            value={props.answer ?? ''}
          />
          
          {
            (props.helper && props.helper.visible) ? 
            <Error text={props.helper.value} /> :
            <Action {...props} next={() => {
              if(props.next) {
                if(isAndroid) {
                  setTimeout(props.next, 250)
                } else {
                  props.next()
                }
              }
              
            }} />
          }
        </form>
      </Reveal>
    </section>
  )
} 
