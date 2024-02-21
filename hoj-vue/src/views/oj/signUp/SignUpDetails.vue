<template>
  <div>
    <el-row type="flex" justify="space-around">
      <el-col :span="24">
        <el-card shadow>
          <div slot="header">
            <el-row>
              <el-col :xs="24" :sm="24" :md="2" :lg="2">
                <span class="panel-title">{{ $t('m.All') }} {{ $t('m.SignUp') }}</span>
              </el-col>
              <el-col v-if="isSuperAdmin" :xs="24" :sm="24" :md="16" :lg="16">
                <span style="line-height: 2vw;">
                  <el-dropdown
                    @command="onStatusChange"
                    placement="bottom"
                    trigger="hover"
                    class="drop-menu"
                  >
                    <span class="el-dropdown-link">
                      {{
                      query.type === ''
                      ? $t('m.SignUpStatus')
                      : parseSignUpType(query.type)
                      }}
                      <i
                        class="el-icon-caret-bottom"
                      ></i>
                    </span>
                    <el-dropdown-menu slot="dropdown">
                      <el-dropdown-item command="0">{{$t('m.All')}}</el-dropdown-item>
                      <el-dropdown-item command="200">Accept</el-dropdown-item>
                      <el-dropdown-item command="300">Apply For Update</el-dropdown-item>
                      <el-dropdown-item command="400">Reject</el-dropdown-item>
                      <el-dropdown-item command="500">Pending</el-dropdown-item>
                    </el-dropdown-menu>
                  </el-dropdown>
                </span>
              </el-col>
              <el-col v-if="isSuperAdmin==false" :xs="24" :sm="24" :md="16" :lg="19">
                <span style="line-height: 2vw;">
                  <el-dropdown
                    @command="onStatusChange"
                    placement="bottom"
                    trigger="hover"
                    class="drop-menu"
                  >
                    <span class="el-dropdown-link">
                      {{
                      query.type === ''
                      ? $t('m.SignUpStatus')
                      : parseSignUpType(query.type)
                      }}
                      <i
                        class="el-icon-caret-bottom"
                      ></i>
                    </span>
                    <el-dropdown-menu slot="dropdown">
                      <el-dropdown-item command="0">{{$t('m.All')}}</el-dropdown-item>
                      <el-dropdown-item command="200">Accept</el-dropdown-item>
                      <el-dropdown-item command="300">Apply For Update</el-dropdown-item>
                      <el-dropdown-item command="400">Reject</el-dropdown-item>
                      <el-dropdown-item command="500">Pending</el-dropdown-item>
                    </el-dropdown-menu>
                  </el-dropdown>
                </span>
              </el-col>
              <el-col v-if="isSuperAdmin" :xs="24" :sm="24" :md="3" :lg="3">
                <el-button
                  type="success"
                  size="medium"
                  plain
                  @click.stop="chooseExportInfo(signUpContestId)"
                  style="margin-left: 30px;"
                >{{$t('m.One_Click_Export')}}</el-button>
                <span></span>
              </el-col>
              <el-col :xs="24" :sm="24" :md="3" :lg="3">
                <el-button
                  v-if="isSignUp==false"
                  type="primary"
                  size="medium"
                  plain
                  @click.stop="toSignUpInfo(signUpContestId)"
                  style="margin-left: 40px;"
                >{{$t('m.SignUpContest')}}</el-button>
                <el-button
                  v-if="isSignUp==true"
                  type="primary"
                  size="medium"
                  plain
                  @click.stop="toSignUpInfo(signUpContestId)"
                  style="margin-left: 40px;"
                >{{$t('m.SignUpModify')}}</el-button>
              </el-col>
            </el-row>
          </div>
          <div v-loading="loading">
            <p id="no-contest" v-show="signUpList.length == 0">
              <el-empty :description="$t('m.No_Sign_Up')"></el-empty>
            </p>
            <ol id="sign-up-list">
              <li
                v-for="signUpItem in signUpList"
                :key="signUpItem.id"
                :style="getborderColor(signUpItem)"
              >
                <el-row
                  type="flex"
                  justify="space-between"
                  align="middle"
                  style="padding: 0 10px 0 0"
                >
                  <el-col :xs="18" :sm="18" :md="20" :lg="20" class="contest-main">
                    <div style="padding: 0 10px;">
                      <p class="title" @click="showTeamInfo(signUpItem)" style="cursor: pointer;">
                        {{ signUpItem.teamName }} | {{signUpItem.englishTeamName}}
                        &emsp;&emsp;
                        <span
                          v-if="signUpItem.teamType===1"
                          style="margin-top: 2px;font-size: 10px;"
                          class="contest-rank-flag"
                        >Girl</span>
                      </p>
                      <div>
                        <el-row>
                          <el-col :xs="24" :sm="24" :md="8" :lg="8" style="font-size: 1vw;">
                            <el-row>
                              <el-col :xs="24" :sm="24" :md="6" :lg="5">
                                <span style="font-weight: 600;font-size: 1vw;">{{$t('m.MemberOne')}}</span>
                              </el-col>
                              <el-col
                                :xs="24"
                                :sm="24"
                                :md="6"
                                :lg="5"
                                style="text-align: center;"
                              >{{ signUpItem.teammatesName.split("-")[0] }}</el-col>
                              <el-col
                                :xs="24"
                                :sm="24"
                                :md="6"
                                :lg="7"
                                style="text-align: center;"
                              >{{ signUpItem.studentId.split("-")[0] }}</el-col>
                              <el-col
                                :xs="24"
                                :sm="24"
                                :md="6"
                                :lg="6"
                                style="text-align: center;"
                              >{{ signUpItem.studentClass.split("-")[0] }}</el-col>
                            </el-row>
                          </el-col>
                          <el-col
                            :xs="24"
                            :sm="24"
                            :md="8"
                            :lg="8"
                            style="font-size: 1vw;"
                            v-show="signUpItem.teammatesName.split('-')[1]!='*'"
                          >
                            <el-row>
                              <el-col :xs="24" :sm="24" :md="8" :lg="5">
                                <span style="font-weight: 600;font-size: 1vw;">{{$t('m.MemberTwo')}}</span>
                              </el-col>
                              <el-col
                                :xs="24"
                                :sm="24"
                                :md="8"
                                :lg="5"
                                style="text-align: center;"
                              >{{ signUpItem.teammatesName.split("-")[1] }}</el-col>
                              <el-col
                                :xs="24"
                                :sm="24"
                                :md="8"
                                :lg="7"
                                style="text-align: center;"
                              >{{ signUpItem.studentId.split("-")[1] }}</el-col>
                              <el-col
                                :xs="24"
                                :sm="24"
                                :md="8"
                                :lg="6"
                                style="text-align: center;"
                              >{{ signUpItem.studentClass.split("-")[1] }}</el-col>
                            </el-row>
                          </el-col>
                          <el-col
                            :xs="24"
                            :sm="24"
                            :md="8"
                            :lg="8"
                            style="font-size: 1vw;"
                            v-show="signUpItem.teammatesName.split('-')[2]!='*'"
                          >
                            <el-row>
                              <el-col :xs="24" :sm="24" :md="8" :lg="5">
                                <span
                                  style="font-weight: 600;font-size: 1vw;"
                                >{{$t('m.MemberThree')}}</span>
                              </el-col>
                              <el-col
                                :xs="24"
                                :sm="24"
                                :md="8"
                                :lg="5"
                                style="text-align: center;"
                              >{{ signUpItem.teammatesName.split("-")[2] }}</el-col>
                              <el-col
                                :xs="24"
                                :sm="24"
                                :md="8"
                                :lg="7"
                                style="text-align: center;"
                              >{{ signUpItem.studentId.split("-")[2] }}</el-col>
                              <el-col
                                :xs="24"
                                :sm="24"
                                :md="8"
                                :lg="6"
                                style="text-align: center;"
                              >{{ signUpItem.studentClass.split("-")[2] }}</el-col>
                            </el-row>
                          </el-col>
                        </el-row>
                      </div>
                    </div>
                  </el-col>
                  <el-col :xs="6" :sm="6" :md="3" :lg="4" style="text-align: right">
                    <el-dropdown
                      v-if="isSuperAdmin"
                      @command="checkApply"
                      placement="bottom"
                      trigger="click"
                      class="drop-menu"
                    >
                      <span @click="getTid(signUpItem.id)" style="cursor: pointer;">
                        <el-tag
                          :type="SIGN_UP_STATUS_REVERSE[signUpItem.status]['type']"
                          size="medium"
                          effect="plain"
                        >{{ $t('m.' + SIGN_UP_STATUS_REVERSE[signUpItem.status]['name']) }}</el-tag>
                      </span>
                      <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item command="200">
                          <span style="color:#8fc31f">Accept</span>
                        </el-dropdown-item>
                        <el-dropdown-item command="400">
                          <span style="color:#ed3f14">Reject</span>
                        </el-dropdown-item>
                      </el-dropdown-menu>
                    </el-dropdown>
                    <span v-if="isSuperAdmin==false" style="cursor: pointer;">
                      <el-tag
                        :type="SIGN_UP_STATUS_REVERSE[signUpItem.status]['type']"
                        size="medium"
                        effect="plain"
                      >{{ $t('m.' + SIGN_UP_STATUS_REVERSE[signUpItem.status]['name']) }}</el-tag>
                    </span>
                    &emsp;&emsp;&emsp;&emsp;
                    <el-tag
                      effect="dark"
                      :color="SIGN_UP_TYPE['1']['color']"
                      size="medium"
                      v-if="signUpItem.star==1"
                    >
                      <i class="fa el-icon-star-on" aria-hidden="true"></i>
                      {{ $t('m.' + SIGN_UP_TYPE['1']['name']) }}
                    </el-tag>
                    <el-tag effect="dark" :color="SIGN_UP_TYPE['0']['color']" size="medium" v-else>
                      <i class="fa fa-circle" aria-hidden="true"></i>
                      {{ $t('m.' + SIGN_UP_TYPE['0']['name']) }}
                    </el-tag>
                  </el-col>
                </el-row>
                <br />
              </li>
            </ol>
          </div>
        </el-card>
        <Pagination
          :total="signUpTotal"
          :page-size="limit"
          @on-change="currentChange"
          :current.sync="currentPage"
          @on-page-size-change="onPageSizeChange"
          :layout="'prev, pager, next, sizes'"
        ></Pagination>
      </el-col>
    </el-row>
    <!-- 查看队伍信息对话框 -->
    <el-dialog
      v-if="isSuperAdmin"
      :title="$t('m.SignUpInfo_View')"
      :visible.sync="dialogVisible"
      width="80%"
      :before-close="handleClose"
    >
      <el-form :model="signUpForm" ref="signUpForm" label-width="100px" :rules="rules+memberNum">
        <p id="teamInfo">{{$t('m.TeamInfo')}}</p>
        <el-row>
          <el-col :xs="24" :sm="24" :md="24" :lg="6">
            <el-form-item prop="teamName" :label="$t('m.TeamName')" required>
              <el-input
                v-model="signUpForm.teamName"
                prefix-icon="el-icon-s-flag"
                :placeholder="$t('m.TeamName')"
                width="100%"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="24" :lg="6">
            <el-form-item prop="englishTeamName" :label="$t('m.EnglishTeamName')" required>
              <el-input
                v-model="signUpForm.englishTeamName"
                :placeholder="$t('m.EnglishTeamName')"
                width="100%"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="24" :lg="6">
            <el-form-item prop="school" :label="$t('m.School')">
              <el-input v-model="signUpForm.school" :placeholder="$t('m.School')" width="100%"></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="24" :lg="5">
            <el-form-item prop="Star" :label="$t('m.IsStar')">
              <el-radio v-model="signUpForm.star" label="0">{{$t('m.Official')}}</el-radio>
              <el-radio v-model="signUpForm.star" label="1">{{$t('m.Star')}}</el-radio>
            </el-form-item>
          </el-col>
        </el-row>
        <p id="teamInfo">{{$t('m.MemberInfo')}}</p>
        <el-row>
          <p id="memberInfo">{{$t('m.MemberOne')}}</p>
          <el-col :xs="24" :sm="24" :md="24" :lg="6">
            <el-form-item prop="teammatesName1" :label="$t('m.TeammatesName')" required>
              <el-input
                v-model="signUpForm.teammatesName1"
                :placeholder="$t('m.TeammatesName')"
                width="100%"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="24" :lg="6">
            <el-form-item prop="studentClass1" :label="$t('m.StudentClass')" required>
              <el-input
                v-model="signUpForm.studentClass1"
                :placeholder="$t('m.StudentClass')"
                width="100%"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="24" :lg="6">
            <el-form-item prop="studentId1" :label="$t('m.StudentId')" required>
              <el-input
                v-model="signUpForm.studentId1"
                :placeholder="$t('m.StudentId')"
                width="100%"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="24" :lg="5">
            <el-form-item prop="nickname1" :label="$t('m.StudentNickName')">
              <el-input
                v-model="signUpForm.nickname1"
                :placeholder="$t('m.StudentNickName')"
                width="100%"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="24" :lg="6">
            <el-form-item prop="usersName1" :label="$t('m.UsersName')" required>
              <el-input
                v-model="signUpForm.usersName1"
                :placeholder="$t('m.UsersName')"
                width="100%"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="24" :lg="6">
            <el-form-item prop="phoneNumber1" :label="$t('m.PhoneNumber')" required>
              <el-input
                v-model="signUpForm.phoneNumber1"
                :placeholder="$t('m.PhoneNumber')"
                width="100%"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="24" :lg="6">
            <el-form-item prop="size1" :label="$t('m.Size')" required>
              <el-input v-model="signUpForm.size1" :placeholder="$t('m.Size')+'S/M/L'" width="100%"></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="24" :lg="6">
            <el-form-item prop="gender1" :label="$t('m.Gender')" required>
              <el-radio v-model="signUpForm.gender1" label="1">{{$t('m.Female')}}</el-radio>
              <el-radio v-model="signUpForm.gender1" label="0">{{$t('m.Male')}}</el-radio>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row v-if="memberNum>1">
          <p id="memberInfo">{{$t('m.MemberTwo')}}</p>
          <el-col :xs="24" :sm="24" :md="24" :lg="6">
            <el-form-item prop="teammatesName2" :label="$t('m.TeammatesName')" required>
              <el-input
                v-model="signUpForm.teammatesName2"
                :placeholder="$t('m.TeammatesName')"
                width="100%"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="24" :lg="6">
            <el-form-item prop="studentClass2" :label="$t('m.StudentClass')" required>
              <el-input
                v-model="signUpForm.studentClass2"
                :placeholder="$t('m.StudentClass')"
                width="100%"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="24" :lg="6">
            <el-form-item prop="studentId2" :label="$t('m.StudentId')" required>
              <el-input
                v-model="signUpForm.studentId2"
                :placeholder="$t('m.StudentId')"
                width="100%"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="24" :lg="5">
            <el-form-item prop="nickname2" :label="$t('m.StudentNickName')">
              <el-input
                v-model="signUpForm.nickname2"
                :placeholder="$t('m.StudentNickName')"
                width="100%"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="24" :lg="6">
            <el-form-item prop="usersName2" :label="$t('m.UsersName')" required>
              <el-input
                v-model="signUpForm.usersName2"
                :placeholder="$t('m.UsersName')"
                width="100%"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="24" :lg="6">
            <el-form-item prop="phoneNumber2" :label="$t('m.PhoneNumber')" required>
              <el-input
                v-model="signUpForm.phoneNumber2"
                :placeholder="$t('m.PhoneNumber')"
                width="70%"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="24" :lg="6">
            <el-form-item prop="size2" :label="$t('m.Size')" required>
              <el-input v-model="signUpForm.size2" :placeholder="$t('m.Size')+'S/M/L'" width="100%"></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="24" :lg="6">
            <el-form-item prop="gender2" :label="$t('m.Gender')" required>
              <el-radio v-model="signUpForm.gender2" label="1">{{$t('m.Female')}}</el-radio>
              <el-radio v-model="signUpForm.gender2" label="0">{{$t('m.Male')}}</el-radio>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row v-if="memberNum===3">
          <p id="memberInfo">{{$t('m.MemberThree')}}</p>
          <el-col :xs="24" :sm="24" :md="24" :lg="6">
            <el-form-item prop="teammatesName3" :label="$t('m.TeammatesName')" required>
              <el-input
                v-model="signUpForm.teammatesName3"
                :placeholder="$t('m.TeammatesName')"
                width="100%"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="24" :lg="6">
            <el-form-item prop="studentClass3" :label="$t('m.StudentClass')" required>
              <el-input
                v-model="signUpForm.studentClass3"
                :placeholder="$t('m.StudentClass')"
                width="100%"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="24" :lg="6">
            <el-form-item prop="studentId3" :label="$t('m.StudentId')" required>
              <el-input
                v-model="signUpForm.studentId3"
                :placeholder="$t('m.StudentId')"
                width="100%"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="24" :lg="5">
            <el-form-item prop="nickname3" :label="$t('m.StudentNickName')">
              <el-input
                v-model="signUpForm.nickname3"
                :placeholder="$t('m.StudentNickName')"
                width="100%"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="24" :lg="6">
            <el-form-item prop="usersName3" :label="$t('m.UsersName')" required>
              <el-input
                v-model="signUpForm.usersName3"
                :placeholder="$t('m.UsersName')"
                width="100%"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="24" :lg="6">
            <el-form-item prop="phoneNumber3" :label="$t('m.PhoneNumber')" required>
              <el-input
                v-model="signUpForm.phoneNumber3"
                :placeholder="$t('m.PhoneNumber')"
                width="70%"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="24" :lg="6">
            <el-form-item prop="size3" :label="$t('m.Size')" required>
              <el-input v-model="signUpForm.size3" :placeholder="$t('m.Size')+'S/M/L'" width="100%"></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="24" :lg="6">
            <el-form-item prop="gender3" :label="$t('m.Gender')" required>
              <el-radio v-model="signUpForm.gender3" label="1">{{$t('m.Female')}}</el-radio>
              <el-radio v-model="signUpForm.gender3" label="0">{{$t('m.Male')}}</el-radio>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </el-dialog>
    <!-- 导出队伍信息对话框 -->
    <el-dialog
      v-if="isSuperAdmin"
      :title="$t('m.Choose_Export_Info')"
      :visible.sync="chooseExportInfoDialogVisible"
      width="70%"
      :before-close="handleClose"
    >
      <div style="margin-top: -30px;">
        <h3>{{$t('m.Choose_Export_Info_Tip')}}</h3>
        <el-checkbox v-model="checked" :label="$t('m.Team_Account')" disabled border size="medium"></el-checkbox>
        <el-checkbox v-model="checked" :label="$t('m.Team_Password')" disabled border size="medium"></el-checkbox>
        <el-checkbox v-model="checked" :label="$t('m.Team_Is_Girls')" disabled border size="medium"></el-checkbox>
        <el-checkbox v-model="checked" :label="$t('m.TeamName')" disabled border size="medium"></el-checkbox>
        <el-checkbox
          v-model="checked"
          :label="$t('m.EnglishTeamName')"
          disabled
          border
          size="medium"
        ></el-checkbox>
        <br />
        <h3>{{$t('m.Choose_Export_Info_Tip_Else')}}</h3>
        <el-checkbox v-model="checked1" :label="$t('m.Team_Member_Account')" border size="medium"></el-checkbox>
        <el-checkbox v-model="checked2" :label="$t('m.Team_Member_Name')" border size="medium"></el-checkbox>
        <el-checkbox v-model="checked3" :label="$t('m.Team_Member_Id')" border size="medium"></el-checkbox>
        <el-checkbox v-model="checked4" :label="$t('m.Team_Member_Class')" border size="medium"></el-checkbox>
        <el-checkbox v-model="checked5" :label="$t('m.Team_Member_Size')" border size="medium"></el-checkbox>
        <el-checkbox v-model="checked6" :label="$t('m.Team_Member_Phone')" border size="medium"></el-checkbox>
        <div style="text-align: center;padding: 20px 0 0 0;">
          <el-button
            type="primary"
            size="medium"
            plain
            @click.stop="exportToCsv()"
            style="width: 200px;"
          >{{$t('m.Admire_Export')}}</el-button>
        </div>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import api from "@/common/api";
import { mapGetters } from "vuex";
import Pagination from "@/components/oj/common/Pagination";
import myMessage from "@/common/message";
import { SIGN_UP_STATUS_REVERSE, SIGN_UP_TYPE } from "@/common/constants";
export default {
  name: "GroupMemberList",
  components: {
    Pagination,
  },
  data() {
    return {
      signUpTotal: 0,
      currentPage: 1,
      teamid: 0,
      cid: 0,
      isSignUp: false,
      limit: 10,
      signUpList: [],
      query: {
        status: "",
        keyword: "",
        type: "",
      },
      signUpForm: {
        // 队伍信息
        teamName: "",
        englishTeamName: "",
        star: 1,
        school: "西南科技大学",
        // 队长
        teammatesName1: "",
        studentClass1: "",
        studentId1: "",
        nickname1: "",
        usersName1: "",
        phoneNumber1: "",
        size1: "",
        gender1: "",
        // 队员二
        teammatesName2: "",
        studentClass2: "",
        studentId2: "",
        nickname2: "",
        usersName2: "",
        phoneNumber2: "",
        size2: "",
        gender2: "",
        // 队员三
        teammatesName3: "",
        studentClass3: "",
        studentId3: "",
        nickname3: "",
        usersName3: "",
        phoneNumber3: "",
        size3: "",
        gender3: "",
      },
      status: 0,
      signUpContestId: this.$route.params.signUpID,
      SIGN_UP_STATUS_REVERSE: {},
      SIGN_UP_TYPE: {},
      loading: false,
      ResultCsv: [],
      dialogVisible: false,
      chooseExportInfoDialogVisible: false,
      memberNum: 1,
      checked: true,
      checked1: false,
      checked2: false,
      checked3: false,
      checked4: false,
      checked5: false,
      checked6: false,
    };
  },
  methods: {
    init() {
      this.getTeamList();
    },
    getTeamList() {
      this.loading = true;
      api
        .getTeamInfoPageByContestId(
          this.status,
          this.signUpContestId,
          this.currentPage,
          this.limit
        )
        .then(
          (res) => {
            this.signUpList = res.data.data.records;
            this.signUpTotal = res.data.data.total;
            this.loading = false;
          },
          (err) => {
            this.loading = false;
          }
        );
    },
    getAcApplyTeam() {
      this.loading = true;
      api
        .getAcApplyTeam(this.signUpContestId, this.currentPage, this.limit)
        .then(
          (res) => {
            this.signUpList = res.data.data;
            this.signUpTotal = res.data.data.length;
            this.loading = false;
          },
          (err) => {
            this.loading = false;
          }
        );
    },
    chooseExportInfo(cid) {
      this.cid = cid;
      this.chooseExportInfoDialogVisible = true;
    },
    exportToCsv() {
      let cid = this.cid;
      let choose = "";
      choose = choose.concat(this.checked6 == true ? "1" : "0");
      choose = choose.concat(this.checked5 == true ? "1" : "0");
      choose = choose.concat(this.checked4 == true ? "1" : "0");
      choose = choose.concat(this.checked3 == true ? "1" : "0");
      choose = choose.concat(this.checked2 == true ? "1" : "0");
      choose = choose.concat(this.checked1 == true ? "1" : "0");
      choose = parseInt(choose, 2);
      api.getAcSignUpCsv(cid, choose).then(
        (res) => {
          myMessage.success(this.$i18n.t("m.DwonloadIng"));
          this.ResultCsv = res.data;
          let csvData = "data:text/csv;charset=utf-8,\uFEFF" + this.ResultCsv;
          this.createDownLoadClick(csvData, `已ac队伍信息.csv`);
        },
        (err) => {}
      );
    },
    createDownLoadClick(content, fileName) {
      const link = document.createElement("a");
      link.href = encodeURI(content);
      link.download = fileName;
      document.body.appendChild(link);
      link.click();
      document.body.vwoveChild(link);
    },
    parseSignUpType(type) {
      if (type == 200) {
        return "Accept";
      } else if (type == 500) {
        return "Pending";
      } else if (type == 400) {
        return "Reject";
      } else if (type == 300) {
        return "Apply For Update";
      } else if (type == 0) {
        return "All";
      }
    },
    showTeamInfo(signUpItem) {
      this.teamid = signUpItem.id;
      api.getTeamInfoById(this.teamid).then((res) => {
        if (res.data.status == 200) {
          let list = res.data.data;
          // this.teamid = list.id
          this.signUpForm.teamName = list.teamName;
          this.signUpForm.englishTeamName = list.englishTeamName;
          this.signUpForm.star = list.star === 0 ? "0" : "1";
          this.signUpForm.school = list.school;
          this.signUpForm.teammatesName1 = list.teammatesName.split("-")[0];
          this.signUpForm.teammatesName2 = list.teammatesName.split("-")[1];
          this.signUpForm.teammatesName3 = list.teammatesName.split("-")[2];
          this.signUpForm.studentClass1 = list.studentClass.split("-")[0];
          this.signUpForm.studentClass2 = list.studentClass.split("-")[1];
          this.signUpForm.studentClass3 = list.studentClass.split("-")[2];
          this.signUpForm.studentId1 = list.studentId.split("-")[0];
          this.signUpForm.studentId2 = list.studentId.split("-")[1];
          this.signUpForm.studentId3 = list.studentId.split("-")[2];
          this.signUpForm.nickname1 = list.nickname.split("-")[0];
          this.signUpForm.nickname2 = list.nickname.split("-")[1];
          this.signUpForm.nickname3 = list.nickname.split("-")[2];
          this.signUpForm.nickname1 =
            list.nickname.split("-")[0] == "*"
              ? ""
              : list.nickname.split("-")[0];
          this.signUpForm.nickname2 =
            list.nickname.split("-")[1] == "*"
              ? ""
              : list.nickname.split("-")[1];
          this.signUpForm.nickname3 =
            list.nickname.split("-")[2] == "*"
              ? ""
              : list.nickname.split("-")[2];
          this.signUpForm.usersName1 = list.usersName.split("-")[0];
          this.signUpForm.usersName2 = list.usersName.split("-")[1];
          this.signUpForm.usersName3 = list.usersName.split("-")[2];
          this.signUpForm.phoneNumber1 = list.phoneNumber.split("-")[0];
          this.signUpForm.phoneNumber2 = list.phoneNumber.split("-")[1];
          this.signUpForm.phoneNumber3 = list.phoneNumber.split("-")[2];
          this.signUpForm.size1 = list.size.split("-")[0];
          this.signUpForm.size2 = list.size.split("-")[1];
          this.signUpForm.size3 = list.size.split("-")[2];
          this.signUpForm.gender1 = list.gender.split("-")[0];
          this.signUpForm.gender2 = list.gender.split("-")[1];
          this.signUpForm.gender3 = list.gender.split("-")[2];
          if (
            this.signUpForm.teammatesName2 != "*" &&
            this.signUpForm.teammatesName3 == "*"
          ) {
            this.memberNum = 2;
          } else if (this.signUpForm.teammatesName3 != "*") {
            this.memberNum = 3;
          }
          this.dialogVisible = true;
        }
      });
    },
    getTid(id) {
      this.teamid = id;
    },
    checkApply(state) {
      this.currentPage = 1;
      let isOk = state == "200" ? true : false;
      api.putTeamStatus(this.signUpContestId, this.teamid, isOk).then((res) => {
        if (res.data.status == 200) {
          this.getTeamList();
        }
      });
    },
    onStatusChange(state) {
      this.query.type = state;
      this.status = state;
      this.currentPage = 1;
      this.getTeamList();
      // if(state!=300){
      //   this.getTeamList();
      // }else{
      //   this.getAcApplyTeam()
      // }
    },
    onPageSizeChange(pageSize) {
      this.limit = pageSize;
      this.init();
    },
    currentChange(page) {
      this.currentPage = page;
      this.init();
    },
    getborderColor(signUpItem) {
      if (signUpItem.teamType === 1) {
        return "padding: 0 5px;border-left: 4px solid #ff99cb";
        // 'padding: 0 5px;border-radius:15px;border-left: 4px solid #ff99cb'
      } else {
        return (
          // 'padding: 0 5px;border-radius:15px;border-left: 4px solid #2ea7e0'
          "padding: 0 5px;border-left: 4px solid #2ea7e0"
          // SIGN_UP_STATUS_REVERSE[signUpItem.status]['color']
        );
      }
    },
    toSignUpInfo(contestId) {
      if (!this.isAuthenticated) {
        myMessage.warning(this.$i18n.t("m.Please_login_first"));
        this.$store.dispatch("changeModalStatus", { visible: true });
      } else {
        this.$router.push({
          name: "SignUpInfo",
          params: { signUpID: contestId },
        });
      }
    },
    getSignUpState() {
      api.getTeamInfoByUserName(this.signUpContestId).then((res) => {
        if (res.data.status == 200) {
          if (res.data.data != "") {
            this.isSignUp = true;
          }
        }
      });
    },
    handleClose(done) {
      done();
      this.memberNum = 1;
    },
  },
  created() {
    this.signUpContestId = this.$route.params.signUpID;
    this.getSignUpState();
  },
  mounted() {
    this.SIGN_UP_STATUS_REVERSE = Object.assign({}, SIGN_UP_STATUS_REVERSE);
    this.SIGN_UP_TYPE = Object.assign({}, SIGN_UP_TYPE);
    this.init();
  },
  computed: {
    ...mapGetters(["isAuthenticated", "userInfo", "isSuperAdmin"]),
  },
};
</script>

<style>
#sign-up-list > li {
  margin-left: -20px;
  margin-top: 10px;
  width: 100%;
  border-bottom: 1px solid rgba(187, 187, 187, 0.5);

  list-style: none;
}
#sign-up-list > li:hover {
  box-shadow: 5px 5px 5px rgba(127, 127, 127, 0.5);
  border-radius: 15px;
  background-color: rgba(176, 175, 175, 0.2);
  transform: translateY(-3px);
  transition: all 0.2s ease-in-out;
}
.title {
  font-size: 1.25vw;
  margin-left: 0 8px;
  margin-top: 5px;
}
</style>