import { FiCheck } from 'react-icons/fi'

export default function Home() {

	return (
		<div data-theme="light" className="w-screen h-screen p-2 lg:p-6 overflow-y-auto scrollbar-hide scroll-smooth">
			<main className="flex flex-col justify-center items-center">
				{/* Question 1*/}
				<section className="hero min-h-screen p-1 pb-12">
					<div className="hero-content text-center w-full max-w-lg">
						<form className="text-left flex flex-col w-full" method="POST" action="javascript:void(0)">
							<h1 className="text-lg font-bold">This is a statement. Be sure your input here is greatly valued :)</h1>
							<p className="pt-2 pb-4 text-sm">You'll find a special gift in your inbox as soon as completed!</p>
							<button className="btn btn-primary self-end relative" onClick={() => {}}>
								Done
								<span className="pt-1 pl-1">
									<img src="/icons/enter.svg" height={11} width={11} />
								</span>
							</button>
						</form>
					</div>
				</section>

				{/* Question 2*/}
				<section className="hero min-h-screen p-1 pb-12">
					<div className="hero-content text-center w-full max-w-lg">
						<form className="text-left flex flex-col w-full" method="POST" action="javascript:void(0)">
							<h1 className="text-lg font-bold">This is a statement. Be sure your input here is greatly valued :)</h1>
							<p className="pt-2 pb-4 text-sm">You'll find a special gift in your inbox as soon as completed!</p>
							<input
								type="text"
								placeholder="Your answer goes here"
								className="input resize-none text-lg mb-6 py-2 bg-accent border-neutral whitespace-nowrap"
							/>
							<button className="btn btn-primary self-end relative" onClick={() => {}}>
								Done
								<span className="pt-1 pl-1">
									<img src="/icons/enter.svg" height={11} width={11} />
								</span>
							</button>
						</form>
					</div>
				</section>

				{/* Question 3 */}
				<section className='hero min-h-screen p-1 pb-12'>
					<div className='hero-content text-center w-full max-w-xl'>
						<div className='text-left flex flex-col w-full'>
							<h1 className='text-lg font-bold'>Are you comfortably seated?</h1>
							<p className='pt-2 pb-4 text-sm'>Getting a nice drink is highly recommended...</p>
							<article className={`grid w-full grid-cols-[repeat(auto-fill,minmax(200px,_1fr))] gap-3 mb-6`}>
								{
									[{ value:"YES"}, { value:"OF COURSE"}].map((option, index) => (
										<div
											className={`
												relative cursor-pointer border-neutral px-10 flex-1 py-2 flex justify-center items-center border rounded-lg
												'bg-accent'}
												''}
											`}
											key={index}
											onClick={() => {}}
										>
											<div
												className={`
													border border-neutral text-[7px] left-3 absolute rounded-sm w-4 h-4 mr-2 flex justify-center items-center
													'border-neutral'}
												`}
											>
												{(index + 10).toString(36).toUpperCase()}
											</div>
											<p className={`whitespace-nowrap text-neutral`} >{option.value}</p>
											{/* {
												(props.answer && props.answer.includes(option.value)) &&
												<span className='pl-1.5 absolute right-3'>
													<FiCheck size={14} class='stroke-accent' />
												</span>
											} */}
										</div>
									))
								}
							</article>
							{/* {
								(props.helper && props.helper.visible) ? 
								<Error text={props.helper.value} /> :
								<Action {...props} />
							} */}
							
						</div>
					</div>
				</section>
			</main>
		</div>
	);
}
