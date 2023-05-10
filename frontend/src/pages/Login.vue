<template>
  <div class="form-signin w-100 m-auto">
    <!-- <form> -->
    <h1 class="h3 mb-3 fw-normal">Please sign in</h1>

    <div class="form-floating">
      <input
        type="email"
        class="form-control"
        id="floatingInput"
        placeholder="name@example.com"
        @keyup.enter="submit()"
        v-model="state.form.email"
      />
      <label for="floatingInput">Email address</label>
    </div>
    <div class="form-floating">
      <input
        type="password"
        class="form-control"
        id="floatingPassword"
        placeholder="Password"
        @keyup.enter="submit()"
        v-model="state.form.password"
      />
      <label for="floatingPassword">Password</label>
    </div>

    <div class="checkbox mb-3">
      <label>
        <input type="checkbox" value="remember-me" @keyup.enter="submit()" />
        Remember me
      </label>
    </div>
    <button class="w-100 btn btn-lg btn-primary" @click="submit()">
      Sign in
    </button>
    <p class="mt-5 mb-3 text-body-secondary">&copy; 2017–2023</p>
    <!-- </form> -->
  </div>
</template>
<script>
import router from "@/scripts/router";
import store from "@/scripts/store";
import axios from "axios";
import { reactive } from "vue";

export default {
  setup() {
    const state = reactive({
      form: {
        email: "",
        password: "",
      },
    });

    const submit = () => {
      axios
        .post("/api/account/login", state.form)
        .then((res) => {
          store.commit("setAccount", res.data);
          console.log(res.data);
          sessionStorage.setItem("id", res.data); //로그인이 풀리는 문제해결 위한 것인데 이것은 좋은 방법이 아님
          console.log(res);
          window.alert("로그인 하였습니다");
          router.push({ path: "/" });
        })
        .catch(() => {
          window.alert("로그인 정보가 존재하지 않습니다...");
        });
    };

    return { state, submit };
  },
};
</script>

<style scoped>
.form-signin {
  max-width: 330px;
  padding: 15px;
}

.form-signin .form-floating:focus-within {
  z-index: 2;
}

.form-signin input[type="email"] {
  margin-bottom: -1px;
  border-bottom-right-radius: 0;
  border-bottom-left-radius: 0;
}

.form-signin input[type="password"] {
  margin-bottom: 10px;
  border-top-left-radius: 0;
  border-top-right-radius: 0;
}
</style>
