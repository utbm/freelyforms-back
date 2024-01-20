import { TbFaceIdError } from 'react-icons/tb'

import Reveal from '../reveal'

interface Props {
  text: string | null
}

export default function Error ( props: Props ) {
  
  return <Reveal delay={0} duration={.1} from='right' className='animate-wiggle-once h-[38px] my-[5px] px-2 text-error border-2 border-error rounded w-fit self-end flex gap-2 items-center'>
    <TbFaceIdError size={22} />
    { props.text ?? '' }
  </Reveal>
}