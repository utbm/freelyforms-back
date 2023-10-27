import { useRef, useEffect } from 'react'

// This hook handles the entire keyboard controls logic over the forms.

export function useControls ( move: (direction: string) => void, setLetter: (keycode: number | null) => void ) {
  const survey = useRef<HTMLElement | null>(null)
  const touch = useRef<number | null>(null)

  useEffect(() => {
    // space, enter & arrows
    window.onkeydown = (e: KeyboardEvent) => {
      const isWriting = e?.isComposing;
      
      if ((e.key == " " ||
          e.code == "Space" ||      
          e.keyCode == 32) && !isWriting 
      ) {
        e.preventDefault()
      }
      // up arrow
      if(e.keyCode === 38) {
        e.preventDefault()
        move('prev')
      }
      // down arrow
      if(e.keyCode === 40) {
        e.preventDefault()
        move('next')
      }
      // enter
      if(e.keyCode === 13) {
        if(e.getModifierState('Shift')) {
          return
        }
        e.preventDefault()
        move('next')
      }

      // letters
      if(e.keyCode >= 65 && e.keyCode <= 90) {
        setLetter(e.keyCode)
        setTimeout(() => {
          setLetter(null)
        }, 300);
      }
    }
    
    if(survey.current) {
      // mouse wheel
      survey.current.onwheel = e => {
        e.preventDefault()
        if(e.deltaY > 10) {
          move('next')
        }
        if(e.deltaY < -10) {
          move('prev')
        }
      }
      // touch events
      survey.current.ontouchstart = e => {
        touch.current = e.touches[0].screenY
      }

      survey.current.ontouchmove = e => e.preventDefault()

      survey.current.ontouchend = e => {
        const t = e.changedTouches[0]
        if(!t || !touch.current) {
          return
        } 
        if(t.screenY - touch.current > 5) {
          setTimeout(() => {
            move('prev')
          }, 50);
        }
        if(t.screenY - touch.current < -5) {
          setTimeout(() => {
            move('next')
          }, 50);
        }
        touch.current = null
      }
    }
    
  }, [])

  return survey
}