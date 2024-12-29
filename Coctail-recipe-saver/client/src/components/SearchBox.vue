<template>
      <div id="search-box">
        <input type="text" name="search" id="search" v-on:keyup.enter="search()" v-model="searchString" placeholder="Search..."/>
      </div>
  </template>
  
  <script>
  export default {
    props: {
      delay: {
        type: Number,
        default: 1000,
      },
    },
    data() {
      return {
        searchString: "",
      };
    },
    methods: {

      // Perform the search based on which route you are in
      search() {
        this.$router.push({ name: this.$route.name , query: { searchTerm: this.searchString } });
      },
    },
  };
  </script>
  
  <style scoped>
  #search {
    font-size: 1em;
    border: none;
    color: #283618;
    padding: 5px
  }
  
  #search:focus-visible {
    outline: #283618 solid 2px;
    border-radius: 5px;
    opacity: 0.75;
  }
  
  </style>