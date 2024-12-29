<template>
  <div class="home">
    <!-- <div id="heading-line">
      <h1>
        Home
        <loading-spinner id="spinner" v-bind:spin="isLoading" />
      </h1>
    </div>
    <h2>Loading spinner demonstration</h2>
    <p>
      This is a demonstration of how you can show or hide a "spinner" icon to
      let the user know something is happening. Before calling an API, you'd set
      the data property <code>isLoading</code> to <code>true</code>. When the
      call completes, set it to <code>false</code>.
    </p>
    <p>
      For this demonstration, clicking the checkbox below sets
      <code>isLoading</code> to <code>true</code>, so it simulates what the user
      would see when your app is accessing an API.
    </p>
    <input type="checkbox" name="loading" id="loading" v-model="isLoading" /> Is
    Loading
    <p id="login-message" v-if="!isLoggedIn">
      Welcome! You may browse anonymously as much as you wish,<br />
      but you must
      <router-link v-bind:to="{ name: 'login' }">Login</router-link> to add
      items to your shopping cart.
    </p>
    <h2>Font-awesome demonstration</h2>
    <p>
      This code shows you how you can include Font-awesome icons on your page. Below are two icons:
      one to indicate a "tile" view of products, and another to indicate a "table" view. There's also a little bit
      of styling to get you started, but you can style it your own way. And there's a property to track which view is "active".
    </p>
    <font-awesome-icon
      v-bind:class="{ 'view-icon': true, active: cardView }"
      v-on:click="cardView = true"
      icon="fa-solid fa-grip"
      title="View tiles"
    />
    <font-awesome-icon
      v-bind:class="{ 'view-icon': true, active: !cardView }"
      v-on:click="cardView = false"
      icon="fa-solid fa-table"
      title="View table"
    /> -->
    <user-cocktails v-bind:cocktails="cocktailData" id="cocktails" />
  </div>
</template>

<script>
import LoadingSpinner from "../components/LoadingSpinner.vue";
import UserCocktails from "../components/UserCocktails.vue";
import CocktailService from "../services/CocktailService";

export default {
  components: {
    // LoadingSpinner,
    UserCocktails
  },
  data() {
    return {
      isLoading: false,
      cardView: true,
      cocktails: []
    };
  },
  methods: {
    getCocktails() {
      CocktailService.get().then(response => {
        this.cocktails = response.data;
      });
    }
  },
  computed: {
    isLoggedIn() {
      return this.$store.state.token.length > 0;
    },
    cocktailData() {
      const searchTerm = this.$route.query.searchTerm;
      if (searchTerm != null) {
        return this.cocktails.filter(cocktail => cocktail.name.toLowerCase().includes(searchTerm.toLowerCase()));
      }

      return this.cocktails
    },
  },
  created() {
      this.getCocktails();
    }
  };
</script>

<style scoped>
#spinner {
  color: green;
}

.view-icon {
  font-size: 1.2rem;
  margin-right: 7px;
  padding: 3px;
  color: #444;
  border-radius: 3px;
}

.view-icon.active {
  background-color: lightgreen;
}

.view-icon:not(.active) {
  font-size: 1.2rem;
  margin-right: 7px;
  cursor: pointer;
}

.view-icon:not(.active):hover {
  color: blue;
  background-color: rgba(255, 255, 255, 0.7);
}
</style>