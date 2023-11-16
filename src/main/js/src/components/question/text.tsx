import TextareaAutosize from 'react-textarea-autosize'
import { useEffect, useMemo } from 'react'
import MobileDetect from 'mobile-detect'

import type { Question } from './index'
import Error from './error'
import Reveal from '../reveal'
import Action from '../action'

interface TextQuestion extends Question {
  answer?: string | null,
  setAnswer: (val: string | null) => void
}

export default function Text( props: TextQuestion ) {
  
  const updateHelper = (val?: string | null) => {
    if(!props.required) {
      props.setHelper({ value: null, visible: false })
    } else {
      const a = val !== undefined ? val : props.answer
      if(!a || !a.length) {
        props.setHelper({ value: 'Please fill this in' })
      } else if(props.type === 'email' && !isEmail(a)) {
        props.setHelper({ value: 'Your email seems invalid' })
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
    const normalized = (val && val.length > 0) ? val : null
    props.setAnswer(normalized)
    updateHelper(normalized)
  }

  return (
    <section className='hero min-h-screen p-1 pb-12' id={`q${props.id}`}>
      <Reveal duration={.2} className='hero-content text-center w-full max-w-lg'>
        <form className='text-left flex flex-col w-full' method='POST' action='javascript:void(0)'>
          <h1 className='text-lg font-bold'>{props.main}</h1>
          <p className='pt-2 pb-4 text-sm'>{props.desc}</p>
          {
            (props.type !== 'long text' && props.autocomplete) ? 
            <input
              type='text'
              name={props.type === 'email' ? 'email' : `q${props.id}-focus`}
              id={`q${props.id}-focus`}
              placeholder='Your answer goes here'
              className='input resize-none text-lg mb-6 py-2 bg-accent border-neutral whitespace-nowrap'
              onChange={e => setAnswer(e.target.value)}
              value={props.answer ?? ''}
              autoComplete={props.autocomplete || 'off'}
            /> :
            <TextareaAutosize
              name={props.type === 'email' ? `email` : `q${props.id}-focus`}
              id={`q${props.id}-focus`}
              placeholder='Your answer goes here'
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
              if(isAndroid) {
                setTimeout(props.next, 250)
              } else {
                props.next()
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
    /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(\.+\))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
  )
}