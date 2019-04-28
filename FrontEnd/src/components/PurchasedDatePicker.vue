<template>
    <div class="date-filter">
        <el-switch
                v-model="filter"
                active-color="#13ce66"
                inactive-color="#ff4949"
                @change="this.handleChange">
        </el-switch>
        <div>日期筛选</div>
        <div>
            <el-date-picker
                    v-model="value"
                    type="daterange"
                    :picker-options="pickerOptions"
                    range-separator="至"
                    start-placeholder="开始日期"
                    end-placeholder="结束日期"
                    align="right"
                    :disabled="!filter"
                    @change="this.handleChange">
            </el-date-picker>
        </div>
    </div>
</template>

<script>
    export default {
        name: "PurchasedDatePicker",
        data() {
            return {
                pickerOptions: {
                    shortcuts: [{
                        text: "最近一周",
                        onClick(picker) {
                            const end = new Date();
                            const start = new Date();
                            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
                            picker.$emit("pick", [start, end]);
                        }
                    }, {
                        text: "最近一个月",
                        onClick(picker) {
                            const end = new Date();
                            const start = new Date();
                            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
                            picker.$emit("pick", [start, end]);
                        }
                    }, {
                        text: "最近三个月",
                        onClick(picker) {
                            const end = new Date();
                            const start = new Date();
                            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
                            picker.$emit("pick", [start, end]);
                        }
                    }],
                    disabledDate(time) {
                        //console.log(Date.now());
                        return time.getTime() > Date.now();
                    }
                },
                value: null,
                filter: true,
            };
        },
        created() {
            const end = new Date();
            const start = new Date();
            start.setDate(start.getDate() - 30);
            this.$store.commit("setTime", {minDate: start, maxDate: end});
            this.value = [start, end];
        },
        methods: {
            handleChange() {
                if (this.filter) {
                    this.$store.commit("setTime", {minDate: this.value[0], maxDate: this.value[1]});
                } else {
                    this.$store.commit("setTime", {minDate: new Date(0), maxDate: new Date()});
                }
            }
        }
    }
</script>

<style scoped>
    .date-filter {
        display: flex;
        align-items: center;
        justify-content: center;
    }
    .date-filter div {
        margin: 10px;
    }
</style>
