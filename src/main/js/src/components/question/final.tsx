import { useEffect, useState } from 'react'
import Confetti from 'react-dom-confetti'

import Reveal from '../reveal'
import { Question } from '.'

interface FinalQuestion extends Question {
  confetti?: boolean
}

export default function Final( props: FinalQuestion ) {
  const confettiConfig = {
    angle: 51,
    spread: 360,
    startVelocity: 40,
    elementCount: 70,
    dragFriction: 0.12,
    duration: 3000,
    stagger: 3,
    width: "10px",
    height: "10px",
    perspective: "500px",
    colors: ["#a864fd", "#29cdff", "#78ff44", "#ff718d", "#fdff6a"]
  }

  const [celebrate, setCelebrate] = useState(false)

  useEffect(() => {
    if(props.confetti && props.current === props.id && !celebrate) {
      setTimeout(() => {
        setCelebrate( true )
      }, 500)
    }
  }, [props.current])

  return (
    <>
    <Confetti active={ celebrate } config={ confettiConfig }/>
    <section className='hero min-h-screen p-1 pb-12' id={`q${props.id}`}>
      <Reveal  duration={.2} className='hero-content text-center w-full max-w-xl'>
        <div className='max-w-md lg:max-w-2xl text-center flex flex-col w-full'>
          <h1 className='text-3xl font-bold'>{props.main}</h1>
          <p className='mt-3 pt-2 pb-6 text-xl font-light'>{props.desc}</p>
        </div>
      </Reveal>
    </section>
  </>
  )
} 