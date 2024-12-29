<template>
  <div class="home">
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
      })
      .catch (error => {
        this.handleErrorResponse(error, "getting")
      });
    },
    handleErrorResponse(error, verb) {
            if (error.response) {
                if (error.response.status == 404) {
                    this.$router.push({ name: 'NotFoundView' });
                } else if (error.response.status == 403) {
                    this.$router.push({ name: 'NoAccessView'})
                } else {
                    this.$store.commit('SET_NOTIFICATION',
                        `Error ${verb} cocktails. Response received was "${error.response.statusText}".`);
                }
            } else if (error.request) {
                this.$store.commit('SET_NOTIFICATION', `Error ${verb} cocktails. Server could not be reached.`);
            } else {
                this.$store.commit('SET_NOTIFICATION', `Error ${verb} cocktails. Request could not be created.`);
            }
        }
  },
  computed: {
    isLoggedIn() {
      return this.$store.state.token.length > 0;
    },

    // Check if there is a search term in the query and then filter the cocktails based on that.
    // If no search term then just return all the cocktails for the user.
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