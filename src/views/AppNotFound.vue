<template>
  <div id="notfound">
    <div>
      <h2 id="title">Don't click the button</h2>
      <button id="donotclick" class="btn btn-danger" @click="play">
        Don't click!!!
      </button>
    </div>
    <audio controls hidden ref="song">
      <source
        src="https://www.cjoint.com/doc/16_09/FIsxS52QXY7_Rick-Astley---Never-Gonna-Give-You-Up.mp3"
        type="audio/mpeg"
      />
      Your browser does not support the audio element.
    </audio>
    <div v-show="isOn" class="background" @click="stop"></div>
  </div>
</template>

<script>
export default {
  name: "app-not-found",
  data: function () {
    return {
      isOn: false,
    };
  },
  methods: {
    play: function () {
      this.isOn = true;
      this.$refs.song.play();
    },
    stop: function () {
      this.isOn = false;
      this.$refs.song.pause();
    },
  },
};
</script>

<style lang="scss">
#notfound,
#notfound .background {
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  position: fixed;
  z-index: 10000;
}
#notfound {
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;
  padding: 0 10px;

  & > div {
    flex-grow: 1;
  }

  & * {
    font-size: 3rem !important;
  }

  & #title {
    margin: 2rem 0;
  }

  & #donotclick {
    padding: 0.8rem 1.6rem;
  }

  & .background {
    background-image: url(https://media.giphy.com/media/Ju7l5y9osyymQ/giphy.gif);
    background-position: center;
  }
}
</style>
