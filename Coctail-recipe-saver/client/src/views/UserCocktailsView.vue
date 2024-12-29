<template>
    <div class="home">
        <public-users :users="userData" />
    </div>
</template>

<script>
import PublicUsers from '../components/PublicUsers.vue';
import UserService from '../services/UserService';

export default {
    components: {
        PublicUsers
    },
    data() {
        return {
            users: []
        }
    },
    methods: {
        getUsers() {
            UserService.get().then(response => {
                this.users = response.data;
            })
        }
    },
    computed: {
        userData() {
            const searchTerm = this.$route.query.searchTerm;
            if (searchTerm != null) {
                return this.users.filter(user => user.username.toLowerCase().includes(searchTerm.toLowerCase()));
            }

            return this.users
        },
    },
    created() {
        this.getUsers();
    }
}
</script>

<style></style>