<template>
  <div id="app">
    <img src="./assets/cbm.png">
    <div>
      <Input search style="width: 400px" />
    </div>
    <ResultsComponent msg="Marc Teufel" />
  </div>
</template>

<script>
import ResultsComponent from './components/ResultsComponent.vue';

export default {
  name: 'app',
  components: {
    ResultsComponent,
  },
};
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}

img {
  width: 150px;
}


</style>
