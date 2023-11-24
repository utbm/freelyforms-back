import { BsSkipEnd } from 'react-icons/bs'

interface Props {
  hidden?: boolean,
  next?: () => void,
  answer?: string | string[] | null,
  required?: boolean,
  action?: string
}

export default function Action( props: Props ) {
  return <button
    className={`btn btn-primary self-end ${props.hidden ? 'opacity-0 pointer-events-none' : ''}`}
    onClick={props.next}
  >
    { (!props.answer && !props.required) ? 'Skip' : props.action ? props.action : 'Done' }
    <span className={`pt-0.5 pl-1.5 ${(props.answer || props.required) ? '' : 'hidden'}`}>
      <img 
        src='/icons/check.svg'
        height={11}
        width={14}
      />
    </span>
    <span className={`pl-1 ${(!props.answer && !props.required) ? '' : 'hidden'}`}>
      <BsSkipEnd size={15} />
    </span>
  </button>
}