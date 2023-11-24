import { MdAccessTimeFilled } from 'react-icons/md'

import Reveal from '../reveal'
import type { Question } from './index'

interface Statement extends Question {
  duration?: number | null
}

export default function Statement( props: Statement ) {

  return (
    <section className='hero min-h-screen p-1 pb-12' id={`q${props.id}`}>
      <Reveal duration={.2} className='hero-content text-center w-full max-w-xl'>
        <div className='max-w-md text-left flex flex-col w-full'>
          <h1 className='text-lg font-bold'>{props.main}</h1>
          <p className='pt-2 pb-6 text-sm'>{props.desc}</p>
          <button
            className='btn btn-primary self-end relative'
            onClick={props.next}
          >
          { props.isLast ? 'Submit'  : props.action ? props.action : 'Done' }
            <span className='pt-1 pl-1'>
              <img 
                src='/icons/enter.svg'
                height={11}
                width={11}
              />
            </span>
            {
              props.duration &&
              <div className='absolute text-xs bottom-[-25px] text-base-200 font-normal normal-case flex justify-center items-center gap-1'>
                <MdAccessTimeFilled size={15}/>
                Takes {props.duration}
              </div>
            }
          </button>
        </div>
      </Reveal>
    </section>
  )
} 