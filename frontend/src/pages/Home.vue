<template>
  <div class="home">
    <div class="album py-5 bg-body-tertiary">
      <div class="container">
        <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
          <div class="col" v-for="(item, idx) in state.items" :key="idx">
            <!-- item은 객체임 -->
            <Card v-bind:item="item" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import Card from "@/components/Card.vue";
import axios from "axios";
import { reactive } from "vue";
export default {
  name: "Home",
  props: {},
  components: { Card },
  setup() {
    const state = reactive({
      items: [],
    });

    //메인페이지가 시작되면서 axios를 통해 자료를 가져오고 셋팅함
    // axios.get("/api/items").then(({data}) => {
    axios.get("/api/items").then((res) => {
      console.log(res);
      state.items = res.data;
      // state.items = data
    });

    return { state };
  },
};
</script>
