<template>
    <div>
      <user-cocktails v-bind:cocktails="cocktailData" :user="user" id="cocktails" />
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
    props: ['user'],
    data() {
      return {
        isLoading: false,
        cardView: true,
        cocktails: [],
        userId: this.$route.params.userId
      };
    },
    methods: {
      getCocktails(id) {
        CocktailService.getPublicUsersCocktails(id).then(response => {
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
    watch: {
      '$route.query.searchTerm': {
        immediate: true,
        handler() {
          this.cocktailData; // Update on route change
        }
      }
    },
    created() {
        this.getCocktails(this.userId);
      }
    };
  </script>