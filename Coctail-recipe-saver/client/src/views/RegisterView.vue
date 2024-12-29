<template>
  <div id="register">
    <form v-on:submit.prevent="register">
      <h1>Create Account</h1>
      <div id="fields">
        <label for="username">Username</label>
        <input type="text" id="username" placeholder="Username" v-model="user.username" required autofocus />
        <label for="name">Name</label>
        <input type="text" id="name" placeholder="Name" v-model="user.name" required />
        <label for="password">Password</label>
        <input type="password" id="password" placeholder="Password" v-model="user.password" required />
        <label for="confirmPassword">Confirm password</label>
        <input type="password" id="confirmPassword" placeholder="Confirm Password" v-model="user.confirmPassword"
          required />

        <label for="city">City</label>
        <input type="text" id="city" placeholder="City" v-model="user.city" />

        <label for="state">State</label>
        <input type="text" id="state" placeholder="State" v-model="user.stateCode" maxlength="2" required />

        <div></div>
        <div>
          <button type="submit">Create Account</button>
        </div>
      </div>
      <!-- <hr /> -->
      <router-link v-bind:to="{ name: 'login' }" id="signIn">Sign in!</router-link>
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
        name: "",
        password: "",
        confirmPassword: "",
        city: "",
        stateCode: "",
        role: "user",
      },
    };
  },
  methods: {
    error(msg) {
      alert(msg);
    },
    success(msg) {
      alert(msg);
    },
    register() {
      if (this.user.password != this.user.confirmPassword) {
        this.error("Password & Confirm Password do not match");
      } else {
        authService
          .register(this.user)
          .then((response) => {
            if (response.status == 201) {
              this.success("Thank you for registering, please sign in.");
              this.$router.push({
                path: "/login",
              });
            }
          })
          .catch((error) => {
            const response = error.response;
            if (!response) {
              this.error(error);
            } else if (response.status === 400) {
              if (response.data.errors) {
                // Show the validation errors
                let msg = "Validation error: ";
                for (let err of response.data.errors) {
                  msg += `'${err.field}':${err.defaultMessage}. `;
                }
                this.error(msg);
              } else {
                this.error(response.data.message);
              }
            } else {
              this.error(response.data.message);
            }
          });
      }
    },
  },
};
</script>

<style scoped>
#register {
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
  border-width: 1px;
  background-color: white;
  padding-left: 10px;
}

label {
  display: none;
}

#username,
#name,
#password,
#confirmPassword,
#address,
#city{
  border-bottom: none;
}

#username {
  border-top-left-radius: 10px;
  border-top-right-radius: 10px;
}

#state {
  border-bottom-right-radius: 10px;
  border-bottom-left-radius: 10px;
}

#signIn {
  margin-top: 10px;
  display: flex;
  justify-content: flex-end;
  color: #65A2A8;
  text-decoration: none;
}
</style>
