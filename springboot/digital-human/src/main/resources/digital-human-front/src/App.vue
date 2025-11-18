<template>
  <div id="app">
    <router-view v-if="$route"/>
    <VoiceChat v-else/>
  </div>
</template>

<script>
import VoiceChat from './components/VoiceChat'

export default {
  name: 'App',
  components: {VoiceChat},
  computed: {
    $route() {
      // 简单的路由检测
      return window.location.hash ? window.location.hash.substring(1) : null
    }
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 20px;
  padding: 20px;
}

* {
  box-sizing: border-box;
}

body {
  margin: 0;
  padding: 0;
}
</style>