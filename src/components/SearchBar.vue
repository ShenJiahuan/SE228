<template>
    <div class="search-bar">
        <el-input
                placeholder="解忧杂货店"
                prefix-icon="el-icon-search"
                v-model="keyword"
                @keyup.enter.native="search">
        </el-input>
    </div>
</template>

<script>
    export default {
        name: "SearchBar",
        data() {
            return {
                keyword: "",
            }
        },
        mounted: function() {
            this.keyword = this.$route.params.keyword;
        },
        methods: {
            search: function() {
                if (this.keyword === undefined || this.keyword === "") {
                    this.keyword = '';
                    console.log("ban");
                } else {
                    this.$router.push('/list/' + this.keyword);
                }
            }
        }
    }
</script>

<style scoped>
    .search-bar {
        width: 400px;
        margin: 20px auto;
    }
</style>