import Statement from './statement'
import Final from './final'
import Text from './text'
import Select from './select'

export interface Helper {
  value: string | null, visible?: boolean
}

export interface Question {
  id: number,
  current?: number | null,
  main?: string,
  desc?: string,
  autocomplete?: string,
  required?: boolean,
  type?: string,
  next?: () => void,
  move?: (dir:string) => void,
  hidden?: boolean,
  isLast?: boolean,
  action?: string,
  helper?: Helper,
  setHelper?: (val: Helper) => void,
  duration?: number | null,
  letter?: number,
  condition?: {
    question: number,
    includes?: string | null,
    answered?: boolean
  }[]
}

interface GenericQuestion extends Question {
  setAnswer: (answers: {[key: string|number]: (string | string[])}) => void,
  setHelpers: (helpers: {[key: string|number]: Helper}) => void,
  answer: string | string[]
}

export default function Question( props: GenericQuestion ) {

  const custom = {
    ...props,
    next: props.move ? props.move.bind(null, 'next') : () => {},
    prev: props.move ? props.move.bind(null, 'prev') : () => {},
    setAnswer: (answer: string | string[]) => {
      const res: {[key: string|number]: (string | string[])} = {}
      res[props.id] = answer
      return props.setAnswer(res)
    },
    setHelper: (helper: Helper) => {
      const res: {[key: string|number]: Helper} = {}
      res[props.id] = { ...props.helper, ...helper}
      return props.setHelpers(res)
    }
  }

  switch(props.type) {
    case 'statement':
      return <Statement {...custom} />
    case 'final':
      return <Final {...custom} />
    case 'text':
    case 'long text':
    case 'email':
      return <Text {...custom} />
    case 'select':
      return <Select {...custom} />
    default: 
      return <></>
  }
}