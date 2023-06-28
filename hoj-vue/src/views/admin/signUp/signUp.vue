<template>
    <div class="view">
      <el-card>
        <div slot="header">
          <span class="panel-title home-title">
            {{ title }}
          </span>
        </div>
        <el-form 
          :model="signUp"
          ref="signUp"
          label-position="top"
          :rules="rules">
          <el-row :gutter="20">
            <el-col  :md="16" :xs="24" :span="12">
              <el-form-item
                :label="$t('m.SignUp_Title')"
                prop="title"
                required
              >
                <el-input
                  v-model="signUp.title"
                  :placeholder="$t('m.SignUp_Title')"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col  :md="8" :xs="24" :span="12">
              <el-form-item
                prop="contestId"
                :label="$t('m.SignUp_Contest')"
                required
              >
                <el-input
                  v-model="signUp.contestId"
                  :placeholder="$t('m.SignUp_Contest')"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col
              :md="8"
              :xs="24"
            >
              <el-form-item
                prop="startTime"
                :label="$t('m.SignUp_Start_Time')"
                required
              >
                <el-date-picker
                  style="width: 100%;"
                  v-model="signUp.startTime"
                  @change="changeDuration"
                  type="datetime"
                  :placeholder="$t('m.SignUp_Start_Time')"
                >
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col
              :md="8"
              :xs="24"
            >
              <el-form-item
                prop="endTime"
                :label="$t('m.SignUp_End_Time')"
                required
              >
                <el-date-picker
                  style="width: 100%;"
                  v-model="signUp.endTime"
                  @change="changeDuration"
                  type="datetime"
                  :placeholder="$t('m.SignUp_End_Time')"
                >
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col
              :md="8"
              :xs="24"
            >
              <el-form-item
                :label="$t('m.SignUp_Duration')"
                required
              >
                <el-input
                  v-model="durationText"
                  disabled
                > </el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col
              :md="8"
              :xs="24"
            >
              <el-form-item
                prop="maxParticipants"
                :label="$t('m.Max_Participants')"
                required
              >
                <el-input-number style="width: 50%;" v-model="signUp.maxParticipants"  :min="1" :max="3" ></el-input-number>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col
              :md="8"
              :xs="24"
            >
              <el-form-item
                prop="contestType"
                :label="$t('m.Contest_Rule_Type')"
                required
              >
                <el-radio
                  class="radio"
                  v-model="signUp.contestType"
                  label="ACM"
                  @change="setSealRankTimeDefaultValue"
                  :disabled="disableRuleType"
                >ACM</el-radio>
                <el-radio
                  class="radio"
                  v-model="signUp.contestType"
                  label="OI"
                  :disabled="disableRuleType"
                  @change="setSealRankTimeDefaultValue"
                >OI</el-radio>
              </el-form-item>
            </el-col>
          </el-row>
          
        </el-form>
        <el-button
          type="primary"
          @click.native="saveSignUp"
        >{{
          $t('m.Save')
        }}</el-button>
      </el-card>
    </div>
  </template>
  
  <script>
  import api from "@/common/api";
  import time from "@/common/time";
  import { mapGetters } from "vuex";
  import myMessage from "@/common/message";
  const Editor = () => import("@/components/admin/Editor.vue");
  const RankBox = () => import("@/components/oj/common/RankBox");
  export default {
    name: "CreateSignUp",
    components: {
      Editor,
      RankBox,
    },
    data() {
      return {
        title: "Create SignUp",
        disableRuleType: false,
        signUp: {},
        signUpID:'',
        formRule: {
          prefix: "",
          suffix: "",
          number_from: 0,
          number_to: 10,
          extra_account: "",
        },
        starUserInput: "",
        inputVisible: false,
        durationText:'',
        rules:{
          title: [
            { required: true, message: this.$i18n.t('m.SignUp_Title_Check_Required'), trigger: 'blur' },
          ],
          contestId:[
            { required: true, message: this.$i18n.t('m.SignUp_Contest_Check_Required'), trigger: 'blur' },
          ],
          maxParticipants:[
            { required: true, message: this.$i18n.t('m.Max_Participants_Check_Required'), trigger: 'blur' },
          ],
          startTime:[
            { required: true, message: this.$i18n.t('m.SignUp_Start_Time_Check_Required'), trigger: 'blur' },
          ],
          endTime: [
            { required: true, message: this.$i18n.t('m.SignUp_End_Time_Check_Required'), trigger: 'blur' },
          ],
          contestType:  [
            { required: true, message: this.$i18n.t('m.ContestType_Check_Required'), trigger: 'blur' },
          ]
        }
      };
    },
    mounted() {
      if (this.$route.name === "admin-edit-signUp") {
        this.title = this.$i18n.t("m.Edit_SignUp");
        this.disableRuleType = true;
      } else {
        this.title = this.$i18n.t("m.Create_SignUp");
        this.disableRuleType = false;
      }
      this.getEditInfo()
    },
    watch: {
      $route() {
        if (this.$route.name === "admin-edit-signUp") {
          this.title = this.$i18n.t("m.Edit_SignUp");
          this.disableRuleType = true;
        } else {
          this.title = this.$i18n.t("m.Create_SignUp");
          this.disableRuleType = false;
          this.signUp = {};
        }
      },
    },
    computed: {
      ...mapGetters(["userInfo"]),
    },
    methods: {
  
      getEditInfo(){
        let signUp = {}
        signUp.contestId = this.$route.params.contestId,
        signUp.contestType = this.$route.params.contestType,
        signUp.maxParticipants = this.$route.params.maxParticipants,
        signUp.startTime = this.$route.params.startTime,
        signUp.endTime = this.$route.params.endTime,
        signUp.title = this.$route.params.title,
        this.signUpID = this.$route.params.id
        this.signUp = signUp
      },

      saveSignUp() {
        if(this.$route.name==='admin-create-signUp'){
          if (!this.signUp.title) {
            myMessage.error(
              this.$i18n.t("m.SignUp_Title") +
                " " +
                this.$i18n.t("m.is_required")
            );
            return;
          }
          if (!this.signUp.contestId) {
            myMessage.error(
              this.$i18n.t("m.SignUp_Contest") +
                " " +
                this.$i18n.t("m.is_required")
            );
            return;
          }
          if (!this.signUp.startTime) {
            myMessage.error(
              this.$i18n.t("m.Contest_Start_Time") +
                " " +
                this.$i18n.t("m.is_required")
            );
            return;
          }
          if (!this.signUp.endTime) {
            myMessage.error(
              this.$i18n.t("m.Contest_End_Time") +
                " " +
                this.$i18n.t("m.is_required")
            );
            return;
          }
          if (!this.signUp.maxParticipants) {
            myMessage.error(
              this.$i18n.t("m.Max_Participants") +
                " " +
                this.$i18n.t("m.is_required")
            );
            return;
          }
          if (!this.signUp.contestType) {
            myMessage.error(
              this.$i18n.t("m.ContestType_Check_Required") 
            );
            return;
          }
          api.admin_addSignUp(this.signUp)
          .then((res) => {
            myMessage.success(this.$i18n.t("m.Add_Sign_Up_Success"));
            this.$router.push({
              name: "admin-signUp-list",
              query: { refresh: "true" },
            });
          })
          .catch(() => {});
        }else{
          let data = {
            title: this.signUp.title,
            signUpContestId:this.signUp.contestId,
            maxParticipants:this.signUp.maxParticipants,
            startTime: this.signUp.startTime,
            endTime: this.signUp.endTime,
            contestType: this.signUp.contestType,
            id:this.signUpID
          }
          api.admin_updateSignUp(data)
          .then((res) => {
            myMessage.success(this.$i18n.t("m.Update_Sign_Up_Success"));
            this.$router.push({
              name: "admin-signUp-list",
              query: { refresh: "true" },
            });
          })
          .catch(() => {});
        }
        
      },

      changeDuration() {
        let start = this.signUp.startTime;
        let end = this.signUp.endTime;
        let durationMS = time.durationMs(start, end);
        if (durationMS < 0) {
          this.durationText = this.$i18n.t("m.Contets_Time_Check");
          return;
        }
        if (start != "" && end != "") {
          this.durationText = time.formatSpecificDuration(start, end);
        }
      }, 
    },
  };
  </script>
  <style scoped>
  .userPreview {
    padding-left: 10px;
    padding-top: 20px;
    padding-bottom: 20px;
    color: red;
    font-size: 16px;
    margin-bottom: 10px;
  }
  .input-new-star-user {
    width: 200px;
  }
  </style>