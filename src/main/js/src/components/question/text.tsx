import TextareaAutosize from 'react-textarea-autosize'
import { useEffect, useMemo } from 'react'
import MobileDetect from 'mobile-detect'

import type { Question } from './index'
import Error from './error'
import Reveal from '../reveal'
import Action from '../action'

interface TypeRule {
  value: string,
  name: string
}

interface TextQuestion extends Question {
  answer?: string | string[],
  setAnswer: (val: string) => void,
  rules?: {
    selectorValues: string[],
    typeRules: TypeRule[],
    optional?: boolean
  },
}

export default function Text( props: TextQuestion ) {
  
  const updateHelper = (val?: string | null) => {
    if(!props.setHelper) {
      return;
    }
    if(props.rules?.optional) {
      props.setHelper({ value: null, visible: false })
    } else {
      const a = val !== undefined ? val : props.answer

      if(!a || typeof a !== 'string' || !a.length) {
        props.setHelper({ value: 'Please fill this in' })
      } else if(props.rules?.typeRules.find(rule => rule.name === "EmailRegexMatch") && !isEmail(a)) {
        props.setHelper({ value: 'Your email seems invalid' })
      } else if(props.rules?.typeRules.find(rule => rule.name === "MaximumRule" && /^[0-9]*$/.test(rule.value))) {
        const maxLength = Number(props.rules?.typeRules.find(rule => rule.name === "MaximumRule" && /^[0-9]*$/.test(rule.value))?.value)
        if(props.answer && props.answer.length >= maxLength) {
          props.setHelper({ value: `Please answer in ${maxLength} characters or less` })
        } else {
          props.setHelper({ value: null, visible: false })
        }
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
          {
            (props.type !== 'long text' && props.autocomplete) ? 
            <input
              type='text'
              name={props.rules?.typeRules.find(rule => rule.name === "EmailRegexMatch") ? 'email' : `${props.id}-focus`}
              id={`${props.id}-focus`}
              placeholder='Your answer goes here'
              className='input resize-none text-lg mb-6 py-2 bg-accent border-neutral whitespace-nowrap'
              onChange={e => setAnswer(e.target.value)}
              value={props.answer ?? ''}
              autoComplete={props.autocomplete || 'off'}
            /> :
            <TextareaAutosize
              name={props.rules?.typeRules.find(rule => rule.name === "EmailRegexMatch") ? `email` : `${props.id}-focus`}
              id={`${props.id}-focus`}
              placeholder={props.caption}
              className='input resize-none text-lg mb-6 py-2 bg-accent border-neutral whitespace-nowrap'
              maxRows={10}
              onChange={e => setAnswer(e.target.value)}
              value={props.answer ?? ''}
              autoComplete={props.autocomplete || 'off'}
            />
          }
          
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

function isEmail( email: string ) {
  return email.match(
    // eslint-disable-next-line no-useless-escape
    /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
  )
}