<template>
  <div>
    <el-card>
      <div slot="header">
        <span class="panel-title home-title">{{ $t('m.SignUp') }}</span>
        <div class="filter-row"></div>
      </div>
      <vxe-table
        :loading="loading"
        ref="xTable"
        :data="signUpList"
        auto-resize
        stripe
        align="center"
      >
        <vxe-table-column field="id" width="80" title="ID"></vxe-table-column>
        <vxe-table-column field="contestId" width="80" :title="$t('m.SignUp_Contest')"></vxe-table-column>
        <vxe-table-column field="title" min-width="150" :title="$t('m.SignUp_Title')" show-overflow></vxe-table-column>
        <vxe-table-column :title="$t('m.Status')" width="100">
          <template v-slot="{ row }">
            <el-tag
              effect="dark"
              :color="CONTEST_STATUS_REVERSE[getSignUpStatus(row)].color"
              size="medium"
            >{{ CONTEST_STATUS_REVERSE[getSignUpStatus(row)].name }}</el-tag>
          </template>
        </vxe-table-column>
        <vxe-table-column width="110" :title="$t('m.AC_Team_Count')">
          <template v-slot="{ row }">
            <li style="display: inline-block; padding: 10px 0 0 10px;">
              <i class="el-icon-user-solid" style="color:rgb(48, 145, 242);"></i>
              x{{row.acTeam}}
            </li>
          </template>
        </vxe-table-column>
        <vxe-table-column min-width="180" :title="$t('m.Info')">
          <template v-slot="{ row }">
            <p>Start Time: {{ row.startTime | localtime }}</p>
            <p>End Time: {{ row.endTime | localtime }}</p>
            <p>Created Time: {{ row.createTime | localtime }}</p>
          </template>
        </vxe-table-column>
        <vxe-table-column min-width="80" :title="$t('m.Option')">
          <template v-slot="{ row }">
            <template v-if="isSuperAdmin || userInfo.uid == row.uid">
              <div style="margin-bottom:10px">
                <el-tooltip effect="dark" :content="$t('m.Edit')" placement="top">
                  <el-button
                    icon="el-icon-edit"
                    size="mini"
                    @click.native="goEdit(row)"
                    type="primary"
                  ></el-button>
                </el-tooltip>
                <el-tooltip effect="dark" :content="$t('m.View_SignUp_List')" placement="top">
                  <el-button
                    icon="el-icon-tickets"
                    size="mini"
                    @click.native="goSignUpList(row.id)"
                    type="success"
                  ></el-button>
                </el-tooltip>
              </div>
            </template>
            <el-tooltip effect="dark" :content="$t('m.Delete')" placement="top" v-if="isSuperAdmin">
              <el-button
                icon="el-icon-delete"
                size="mini"
                @click.native="deleteContest(row.id)"
                type="danger"
              ></el-button>
            </el-tooltip>
          </template>
        </vxe-table-column>
      </vxe-table>
      <div class="panel-options">
        <el-pagination
          class="page"
          layout="prev, pager, next"
          @current-change="currentChange"
          :page-size="pageSize"
          :current-page.sync="currentPage"
          :total="total"
        ></el-pagination>
      </div>
    </el-card>
    <el-dialog
      :title="$t('m.Download_Contest_AC_Submission')"
      width="320px"
      :visible.sync="downloadDialogVisible"
    >
      <el-switch v-model="excludeAdmin" :active-text="$t('m.Exclude_admin_submissions')"></el-switch>
      <el-radio-group v-model="splitType" style="margin-top:10px">
        <el-radio label="user">{{ $t('m.SplitType_User') }}</el-radio>
        <el-radio label="problem">{{ $t('m.SplitType_Problem') }}</el-radio>
      </el-radio-group>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="downloadSubmissions">
          {{
          $t('m.OK')
          }}
        </el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import api from "@/common/api";
import utils from "@/common/utils";
import time from "@/common/time";
import {
  CONTEST_STATUS_REVERSE,
  CONTEST_TYPE_REVERSE,
} from "@/common/constants";
import { mapGetters } from "vuex";
import myMessage from "@/common/message";
export default {
  name: "ContestList",
  data() {
    return {
      pageSize: 10,
      total: 0,
      signUpList: [],
      keyword: "",
      loading: false,
      excludeAdmin: true,
      splitType: "user",
      currentPage: 1,
      currentId: 1,
      downloadDialogVisible: false,
      CONTEST_TYPE_REVERSE: {},
    };
  },
  mounted() {
    this.CONTEST_TYPE_REVERSE = Object.assign({}, CONTEST_TYPE_REVERSE);
    this.CONTEST_STATUS_REVERSE = Object.assign({}, CONTEST_STATUS_REVERSE);
    this.getContestList();
    var that = this;
    window.onbeforeunload = (e) => {
      e = e || window.event;
      // 兼容IE8和Firefox 4之前的版本
      if (e) {
        e.returnValue = "关闭提示";
      }
      // Chrome, Safari, Firefox 4+, Opera 12+ , IE 9+
      return "关闭提示";
    };
  },
  watch: {
    $route() {
      let refresh = this.$route.query.refresh == "true" ? true : false;
      if (refresh) {
        this.getContestList();
      }
    },
  },
  computed: {
    ...mapGetters(["isSuperAdmin", "userInfo"]),
  },
  methods: {
    // 切换页码回调
    currentChange(page) {
      this.currentPage = page;
      this.getContestList(page);
    },
    timeUtcToLocal(utcTime) {
      let localTime = time.utcToLocal(utcTime);
      return localTime;
    },
    getContestList() {
      this.loading = true;
      api.getSIgnUpContestPage(this.currentPage, this.pageSize).then((res) => {
        this.loading = false;
        this.total = res.data.length;
        this.signUpList = res.data.data;
        let count = 6;
        for (let i in this.signUpList) {
          let cid = this.signUpList[i].id;
          api.getACTeamCount(cid).then((res) => {
            count = res.data.data;
            this.$set(this.signUpList[i], "acTeam", count);
          });
        }
      });
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
    openDownloadOptions(contestId) {
      this.downloadDialogVisible = true;
      this.currentId = contestId;
    },
    downloadSubmissions() {
      let url = `/api/file/download-contest-ac-submission?cid=${this.currentId}&excludeAdmin=${this.excludeAdmin}&splitType=${this.splitType}`;
      utils.downloadFile(url);
      this.downloadDialogVisible = false;
    },
    goEdit(signUp) {
      // let contestId = signUp.contestId
      this.$router.push({
        name: "admin-edit-signUp",
        params: {
          contestId: signUp.contestId,
          contestType: signUp.contestType,
          createTime: signUp.createTime,
          id: signUp.id,
          maxParticipants: signUp.maxParticipants,
          startTime: signUp.startTime,
          endTime: signUp.endTime,
          title: signUp.title,
        },
      });
    },
    goSignUpList(sid) {
      this.$router.push({
        name: "admin-signUpTeam-list",
        params: { signUpID: sid },
      });
    },
    deleteContest(sid) {
      this.$confirm(this.$i18n.t("m.Delete_SignUp_Tips"), "Tips", {
        confirmButtonText: this.$i18n.t("m.OK"),
        cancelButtonText: this.$i18n.t("m.Cancel"),
        type: "warning",
      }).then(() => {
        api.deleteSignUp(sid).then((res) => {
          myMessage.success(this.$i18n.t("m.Delete_successfully"));
          this.currentChange(1);
        });
      });
    },
    changeContestVisible(contestId, visible, uid) {
      api.admin_changeContestVisible(contestId, visible, uid).then((res) => {
        myMessage.success(this.$i18n.t("m.Update_Successfully"));
      });
    },
    filterByKeyword() {
      this.currentChange(1);
    },
  },
};
</script>
<style scoped>
.filter-row {
  margin-top: 10px;
}
@media screen and (max-width: 768px) {
  .filter-row span {
    margin-right: 5px;
  }
  .filter-row span div {
    width: 80% !important;
  }
}
@media screen and (min-width: 768px) {
  .filter-row span {
    margin-right: 20px;
  }
}
.el-tag--dark {
  border-color: #fff;
}
</style>