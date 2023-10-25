export default function Home() {
  return (
    <div
      data-theme='light'
      className='w-screen h-screen p-2 lg:p-6 overflow-y-auto scrollbar-hide scroll-smooth'
    >
      <main className='flex flex-col justify-center items-center'>
        {/* Question */}
        <section className='hero min-h-screen p-1 pb-12' >
          <div className='hero-content text-center w-full max-w-lg'>
            <form className='text-left flex flex-col w-full' method='POST' action='javascript:void(0)'>
              <h1 className='text-lg font-bold'>
                Ceci est le POC du projet DA50
              </h1>
              <p className='pt-2 pb-4 text-sm'>
                Groupe 3
              </p>
            </form>
          </div>
        </section>
      </main>

    </div>
  );
}