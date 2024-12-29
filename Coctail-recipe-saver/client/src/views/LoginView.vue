<template>
  <div id="login">
    <form v-on:submit.prevent="login">
      <img src="../../design/martini-logo.png" />
      <h1>Please Sign In</h1>
      <div id="fields">
        <!-- <label for="username">Username</label> -->
        <input type="text" id="username" placeholder="Username" v-model="user.username" required autofocus />
        <!-- <label for="password">Password</label> -->
        <input type="password" id="password" placeholder="Password" v-model="user.password" required />
        <div><button type="submit">Sign in</button></div>
      </div>
      <router-link v-bind:to="{ name: 'register' }" id="register">Register</router-link>
    </form>
  </div>
</template>

<script>
import authService from "../services/AuthService";

export default {
  data() {
    return {
      user: {
        username: "",
        password: "",
      },
    };
  },
  methods: {
    login() {
      authService
        .login(this.user)
        .then((response) => {
          if (response.status == 200) {
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);
            this.$router.push("/");
          }
        })
        .catch((error) => {
          const response = error.response;
          if (!response) {
            alert(error);
          } else if (response.status === 401) {
            alert("Invalid username and password!");
          } else {
            alert(response.message);
          }
        });
    },
  },
};
</script>

<style scoped>

img {
  height: 50px;
}
#login {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}

#fields {
  display: flex;
  flex-direction: column;
}

button {
  margin-top: 20px;
  width: 100%;
  border-radius: 7px;
  background-color: #778742;
  color: white;
  border-color: #283618;
  padding: 5px;
  border-width: 1px;
}

input {
  height: 50px;
  width: 250px;
  border-color: black;
  background-color: white;
  padding-left: 10px;
  border-width: 1px;
}

#username {
  border-top-left-radius: 10px;
  border-top-right-radius: 10px;
  border-bottom: none;
}

#password {
  border-bottom-right-radius: 10px;
  border-bottom-left-radius: 10px;
}

#register {
  margin-top: 10px;
  display: flex;
  justify-content: flex-end;
  color: #65A2A8;
  text-decoration: none;
}
h1 {
  border: none;
}
</style>
