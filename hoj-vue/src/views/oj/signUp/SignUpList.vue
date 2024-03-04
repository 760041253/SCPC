<template>
  <div>
    <!-- <ContestListAttention></ContestListAttention> -->
    <el-row type="flex" justify="space-around">
      <el-col :span="24">
        <el-card shadow>
          <div slot="header">
            <span class="panel-title">
              {{
              query.type === '' ? $t('m.All') : parseContestType(query.type)
              }}
              {{ $t('m.SignUp') }}
            </span>
            <div class="filter-row"></div>
          </div>
          <div v-loading="loading">
            <p id="no-contest" v-show="signups.length == 0">
              <el-empty :description="$t('m.No_Sign_Up')"></el-empty>
            </p>
            <ol id="contest-list">
              <li v-for="signUp in signups" :key="signUp.title" :style="getborderColor(signUp)">
                <el-row type="flex" justify="space-between" align="middle">
                  <el-col :xs="10" :sm="4" :md="3" :lg="2">
                    <template v-if="signUp.contestType == 'ACM'">
                      <el-image
                        :src="acmSrc"
                        class="trophy"
                        style="width: 100px;"
                        :preview-src-list="[acmSrc]"
                      ></el-image>
                    </template>
                    <template v-else>
                      <el-image
                        :src="oiSrc"
                        class="trophy"
                        style="width: 100px;"
                        :preview-src-list="[oiSrc]"
                      ></el-image>
                    </template>
                  </el-col>
                  <el-col :xs="10" :sm="16" :md="19" :lg="20" class="contest-main">
                    <p class="title">
                      <a class="entry" @click.stop="toSignUp(signUp)">{{ signUp.title }}</a>
                    </p>
                    <ul class="detail">
                      <el-tooltip
                        :content="$t('m.SignUp')+$t('m.SignUp_Start_Time')"
                        placement="top"
                        effect="light"
                      >
                        <li>
                          <i class="el-icon-time" aria-hidden="true" style="color: #3091f2"></i>
                          {{ timeUtcToLocal(signUp.startTime)}}
                        </li>
                      </el-tooltip>
                      <el-tooltip
                        :content="$t('m.SignUp')+$t('m.SignUp_End_Time')"
                        placement="top"
                        effect="light"
                      >
                        <li>
                          <i
                            class="el-icon-switch-button"
                            aria-hidden="true"
                            style="color: #fc4646"
                          ></i>
                          {{ timeUtcToLocal(signUp.endTime)}}
                        </li>
                      </el-tooltip>

                      <el-tooltip :content="$t('m.AC_Team_Count')" placement="top" effect="light">
                        <li>
                          <i class="el-icon-user-solid" style="color:rgb(48, 145, 242);"></i>
                          x{{signUp.acTeam}}
                        </li>
                      </el-tooltip>
                      <li>
                        <span>{{$t('m.Max_Participants')}} {{signUp.maxParticipants}} {{$t('m.Max_Participants_Unit')}}</span>
                      </li>
                    </ul>
                  </el-col>
                  <el-col :xs="4" :sm="4" :md="2" :lg="2" style="text-align: center">
                    <el-tag
                      effect="dark"
                      :color="CONTEST_STATUS_REVERSE[signUp.status]['color']"
                      size="medium"
                    >
                      <i class="fa fa-circle" aria-hidden="true"></i>
                      {{
                      $t('m.' + CONTEST_STATUS_REVERSE[signUp.status]['name'])
                      }}
                    </el-tag>
                  </el-col>
                </el-row>
              </li>
            </ol>
          </div>
        </el-card>
        <Pagination
          :total="total"
          :pageSize="limit"
          @on-change="onCurrentPageChange"
          :current.sync="currentPage"
        ></Pagination>
      </el-col>
    </el-row>
  </div>
</template>

  <script>
import api from "@/common/api";
import { mapGetters } from "vuex";
import utils from "@/common/utils";
import storage from "@/common/storage";
import time from "@/common/time";
import {
  CONTEST_STATUS_REVERSE,
  CONTEST_TYPE_REVERSE,
  CONTEST_STATUS,
} from "@/common/constants";
import myMessage from "@/common/message";
const Pagination = () => import("@/components/oj/common/Pagination");
const limit = 10;

export default {
  name: "sign-up-list",
  components: {
    Pagination,
    // ContestListAttention
  },
  data() {
    return {
      currentPage: 1,
      currentDate: new Date(),
      timer: null,
      count: 0,
      query: {
        status: "",
        keyword: "",
        type: "",
      },
      limit: limit,
      total: 0,
      rows: "",
      signups: [],
      CONTEST_STATUS_REVERSE: {},
      CONTEST_STATUS: {},
      CONTEST_TYPE_REVERSE: {},
      acmSrc: require("@/assets/acm.jpg"),
      oiSrc: require("@/assets/oi.jpg"),
      loading: true,
    };
  },
  created() {
    let route = this.$route.query;
    this.currentPage = parseInt(route.currentPage) || 1;
  },
  mounted() {
    this.CONTEST_STATUS_REVERSE = Object.assign({}, CONTEST_STATUS_REVERSE);
    this.CONTEST_TYPE_REVERSE = Object.assign({}, CONTEST_TYPE_REVERSE);
    this.CONTEST_STATUS = Object.assign({}, CONTEST_STATUS);
    this.init();
    // this.timer = setInterval(() => {
    //   this.currentDate = new Date();
    // }, 1000);
  },
  beforeDestroy() {
    clearInterval(this.timer);
  },
  methods: {
    init() {
      let route = this.$route.query;
      this.query.status = route.status || "";
      if (route.type === 0 || route.type === "0") {
        this.query.type = 0;
      } else if (route.type === 1 || route.type === "1") {
        this.query.type = 1;
      } else {
        this.query.type = "";
      }
      this.query.keyword = route.keyword || "";
      this.currentPage = parseInt(route.currentPage) || 1;
      this.getContestList();
    },
    getContestList() {
      this.loading = true;
      api.getSIgnUpContestPage(this.currentPage, this.limit).then(
        (res) => {
          this.signups = res.data.data;
          this.total = res.data.data.length;
          this.loading = false;
          let count = 6;
          for (let i in this.signups) {
            let cid = this.signups[i].id;
            api.getACTeamCount(cid).then((res) => {
              count = res.data.data;
              this.$set(this.signups[i], "acTeam", count);
            });
          }
        },
        (err) => {
          this.loading = false;
        }
      );
    },
    filterByChange() {
      let query = Object.assign({}, this.query);
      query.currentPage = this.currentPage;
      this.$router.push({
        name: "ContestList",
        query: utils.filterEmptyValue(query),
      });
    },
    timeUtcToLocal(utcTime) {
      let localTime = time.utcToLocal(utcTime);
      return localTime;
    },
    getSignUpStatus(signUp) {
      let startTime = time.utcToLocal(signUp.startTime);
      let endTime = time.utcToLocal(signUp.endTime);
      let thisTime = time.utcToLocal(this.currentDate);
      let flag = 0;
      if (thisTime > startTime) {
        flag += 1;
      }
      if (thisTime > endTime) {
        flag += 1;
      }
      let signUpStatus = "";
      if (flag == 0) {
        signUpStatus = "-1";
      } else if (flag == 1) {
        signUpStatus = "0";
      } else {
        signUpStatus = "1";
      }
      this.$set(signUp, "status", signUpStatus);
      return signUpStatus;
    },
    parseContestType(type) {
      if (type == 0) {
        return "ACM";
      } else if (type == 1) {
        return "OI";
      }
    },
    onCurrentPageChange(page) {
      this.currentPage = page;
      this.filterByChange();
    },
    onRuleChange(rule) {
      this.query.type = rule;
      this.currentPage = 1;
      this.filterByChange();
    },
    onStatusChange(status) {
      this.query.status = status;
      this.currentPage = 1;
      this.filterByChange();
    },
    onKeywordChange() {
      this.currentPage = 1;
      this.filterByChange();
    },
    toSignUp(signUp) {
      storage.set("maxParticipants", signUp.maxParticipants);
      if (!this.isAuthenticated) {
        myMessage.warning(this.$i18n.t("m.Please_login_first"));
        this.$store.dispatch("changeModalStatus", { visible: true });
      } else {
        this.$router.push({
          name: "SignUpDetails",
          params: { signUpID: signUp.id },
        });
      }
    },
    getborderColor(signUp) {
      this.getSignUpStatus(signUp);
      return (
        "border-left: 4px solid " +
        CONTEST_STATUS_REVERSE[signUp.status]["color"]
      );
    },
  },
  computed: {
    ...mapGetters(["isAuthenticated", "userInfo"]),
    currentTime() {
      const year = this.currentDate.getFullYear();
      const month = `0${this.currentDate.getMonth() + 1}`.slice(-2);
      const day = `0${this.currentDate.getDate()}`.slice(-2);
      const hours = `0${this.currentDate.getHours()}`.slice(-2);
      const minutes = `0${this.currentDate.getMinutes()}`.slice(-2);
      const seconds = `0${this.currentDate.getSeconds()}`.slice(-2);
      return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
    },
  },
  watch: {
    $route(newVal, oldVal) {
      if (newVal !== oldVal) {
        this.init();
      }
    },
  },
};
</script>
  <style scoped>
#no-contest {
  text-align: center;
  font-size: 16px;
  padding: 20px;
}
.filter-row {
  float: right;
}
@media screen and (max-width: 768px) {
  .filter-row span {
    margin-right: 2px;
  }
  ol {
    padding-inline-start: 5px;
  }
  /deep/ .el-card__header {
    margin-bottom: 5px;
  }
}
@media screen and (min-width: 768px) {
  .filter-row span {
    margin-right: 20px;
  }
}
/deep/ .el-card__header {
  border-bottom: 0px;
}

#contest-list > li {
  padding: 12px 5px;
  margin-left: -20px;
  margin-top: 10px;
  width: 100%;
  border-bottom: 1px solid rgba(187, 187, 187, 0.5);
  list-style: none;
}
#contest-list .trophy {
  height: 70px;
  margin-left: 10px;
  margin-right: -20px;
}
@media screen and (max-width: 1500px) and (min-width: 1200px) {
  #contest-list .trophy {
    width: 100% !important;
  }
  #contest-list .contest-main {
    margin-left: 20px;
  }
}
#contest-list .contest-main {
  text-align: left;
}
#contest-list .contest-main .title {
  font-size: 1.25rem;
  padding-left: 8px;
  margin-bottom: 0;
}
#contest-list .contest-main .title a.entry {
  color: #495060;
}
#contest-list .contest-main .title a:hover {
  color: #2d8cf0;
  border-bottom: 1px solid #2d8cf0;
}
#contest-list .contest-main .detail {
  font-size: 0.875rem;
  padding-left: 0;
  padding-bottom: 10px;
}
#contest-list .contest-main li {
  display: inline-block;
  padding: 10px 0 0 10px;
}
</style>
