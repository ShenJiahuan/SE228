<template>
  <el-input class="order-count" name="count" @input="change" @change="change" @blur="setNonNull" :value="this.inputNum" autocomplete="off"/>
</template>

<script>
  export default {
    name: "OrderCountBox",
    props: [
      "num",
      "update",
      "id",
    ],
    data() {
      return {
        inputNum: this.num,
        oldNum: this.num,
      }
    },
    methods: {
      change(event) {
        this.$nextTick(() => {
          let val = event;
          if (/^[1-9]\d*$|^$/.test(val) && val < 1000) {
            this.oldNum = val;
            this.inputNum = val;
          } else if (val >= 1000) {
            this.inputNum = 999;
            this.oldNum = 999;
            this.$notify.error({
              title: "错误",
              message: "超过数量上限"
            });
          } else {
            this.inputNum = this.oldNum;
          }
          this.$emit("update:num", this.inputNum);
        });
      },
      setNonNull(event) {
        let val = event.target.value.trim();
        if (val === "") {
          event.target.value = 1;
          this.$emit("update:num", 1);
        }
        if (this.update) {
          this.update(this.id);
        }
      }
    }
  }
</script>

<style scoped>
  .input {
    width: 60px;
    display: inline;
    margin: 0 10px;
  }
</style>
