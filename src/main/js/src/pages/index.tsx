

export default function Home() {
  
  return (
    <div
      data-theme='light'
      className='w-screen h-screen p-2 lg:p-6 overflow-y-auto scrollbar-hide scroll-smooth'
    >
      <main className='flex flex-col justify-center items-center'>
        {/* Question 1*/}
        <section className='hero min-h-screen p-1 pb-12' >
          <div className='hero-content text-center w-full max-w-lg'>
            <form className='text-left flex flex-col w-full' method='POST' action='javascript:void(0)'>
              <h1 className='text-lg font-bold'>
                This is a statement. Be sure your input here is greatly valued :)
              </h1>
              <p className='pt-2 pb-4 text-sm'>
                You'll find a special gift in your inbox as soon as completed!
              </p>
              <button
                className='btn btn-primary self-end relative'
                onClick={() => {}}
              >
               Done
              <span className='pt-1 pl-1'>
                <img 
                  src='/icons/enter.svg'
                  height={11}
                  width={11}
                />
                </span>
              </button>
            </form>
          </div>
        </section>

        {/* Question 2*/}
        <section className='hero min-h-screen p-1 pb-12' >
          <div className='hero-content text-center w-full max-w-lg'>
            <form className='text-left flex flex-col w-full' method='POST' action='javascript:void(0)'>
              <h1 className='text-lg font-bold'>
                This is a statement. Be sure your input here is greatly valued :)
              </h1>
              <p className='pt-2 pb-4 text-sm'>
                You'll find a special gift in your inbox as soon as completed!
              </p>
              <input
                type='text'
                placeholder='Your answer goes here'
                className='input resize-none text-lg mb-6 py-2 bg-accent border-neutral whitespace-nowrap'
              />
              <button
                className='btn btn-primary self-end relative'
                onClick={() => {}}
              >
               Done
              <span className='pt-1 pl-1'>
                <img 
                  src='/icons/enter.svg'
                  height={11}
                  width={11}
                />
                </span>
              </button>
            </form>
          </div>
        </section>
      </main>

    </div>
  );
}