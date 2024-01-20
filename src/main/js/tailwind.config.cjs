const defaultTheme = require('tailwindcss/defaultTheme')

module.exports = {
  content: [
    './src/pages/**/*.{js,ts,jsx,tsx}',
    './src/components/**/*.{js,ts,jsx,tsx}',
  ],
  theme: {
    extend: {
      fontFamily: {
        sans: ['Lato', ...defaultTheme.fontFamily.sans],
      },
      keyframes: {
        wiggle: {
          '0%': {transform: 'translate(10px)'},
          '25%': {transform: 'translate(-10px)'},
          '50%': {transform: 'translate(10px)'},
          '75%': {transform: 'translate(-5px)'},
          '100%': {transform: 'translate(0px)'}
        }
      },
      animation: {
        'pulse-quick': 'pulse .6s linear infinite',
        'wiggle-once': 'wiggle .6s linear 1'
      }
    },
  },
  daisyui: {
    themes: [
      {
        light: {
          'primary': '#337FF6',
          'secondary': '#F9F9F6',
          'accent': '#EBECEF',
          'neutral': '#434856',
          'base-100': '#F9F9F6',
          'base-200': '#BBC1C4',
          'info': '#A1C2F7',
          'success': '#25D477',
          'warning': '#F6C80E',
          'error': '#E87A76',

          '--font-family': 'Lato, sans-serif'
        },
      },
      {
        dark: {
          'primary': '#337FF6',
          'secondary': '#252932',
          'accent': '#434856',
          'neutral': '#F6F7F8',
          'base-100': '#252932',
          'base-200': '#5F686D',
          'info': '#A1C2F7',
          'success': '#25D477',
          'warning': '#F6C80E',
          'error': '#E87A76',

          '--font-family': 'Lato, sans-serif'
        },
      },
    ],
  },
  plugins: [
    require('@tailwindcss/typography'),
    require('daisyui'),
    require('tailwind-scrollbar-hide'),
  ],
};
