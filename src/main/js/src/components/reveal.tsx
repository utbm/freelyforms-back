import { motion } from 'framer-motion'

interface Props {
  className: string,
  from?: string,
  delay?: number,
  duration?: number,
  children: React.ReactNode
}

export default function Reveal( props: Props ) {
  return (
    <motion.div
      className={props.className}
      initial={{
        opacity: 0,
        y: (() => {
          switch(props.from) {
            case 'bottom':
              return 50
            case 'top':
              return -50
            default: 
              return 0
          }
        })(),
        x: (() => {
          switch(props.from) {
            case 'right':
              return 50
            case 'left':
              return -50
            default: 
              return 0
          }
        })()
      }}
      transition={{
        delay: props.delay || 0,
        duration: props.duration || 1,
        bounce: .4
      }}
      whileInView={{ opacity: 1, y: 0, x: 0 }}
      viewport={{ once: true }}
    >
      { props.children }
    </motion.div>
  )
}