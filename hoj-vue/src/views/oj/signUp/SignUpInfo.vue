<template>
    <div>
        <el-row v-if="isSignUp==false" type="flex" justify="space-around">
            <el-col :span="24">
                <el-card shadow>
                    <div slot="header">
                        <span class="panel-title">
                            {{ $t('m.SignUpInfoTitle') }} <i class="el-icon-edit"></i>
                        </span>
                    </div>
                    <el-form
                        :model="signUpForm"
                        ref="signUpForm"
                        label-width="100px"
                        :rules="rules"
                    >
                    <p id="teamInfo">{{$t('m.TeamInfo')}}</p>
                    <el-row>
                        <el-col :xs="24" :sm="24" :md="24" :lg="6">
                            <el-form-item prop="teamName" :label="$t('m.TeamName')" required>
                                <el-input
                                    v-model="signUpForm.teamName"
                                    prefix-icon="el-icon-s-flag"
                                    :placeholder="$t('m.TeamName')"
                                    width="100%" maxlength="20"
                                ></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="24" :lg="6">
                            <el-form-item prop="englishTeamName" :label="$t('m.EnglishTeamName')" required>
                                <el-input
                                    v-model="signUpForm.englishTeamName"
                                    :placeholder="$t('m.EnglishTeamName')"
                                    width="100%"  maxlength="25"
                                ></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="24" :lg="6">
                            <el-form-item prop="school" :label="$t('m.School')">
                                <el-input
                                    v-model="signUpForm.school"
                                    :placeholder="$t('m.School')"
                                    width="100%" maxlength="17"
                                ></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="24" :lg="6">
                            <el-form-item prop="Star" :label="$t('m.IsStar')">
                                <el-radio v-model="signUpForm.star" label="0">{{$t('m.Official')}}</el-radio>
                                <el-radio v-model="signUpForm.star" label="1">{{$t('m.Star')}}</el-radio>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <p id="teamInfo">{{$t('m.MemberInfo')}}
                        <el-button type="primary" size="small" plain  @click="addMember()">
                            {{$t('m.AddMember')}}</el-button>
                        <el-button type="primary" size="small" plain v-show="memberNum>1"  @click="subMember()">
                            {{$t('m.SubMember')}}</el-button>
                    </p>
                    <el-row>
                      <p id="memberInfo">{{$t('m.MemberOne')}}</p>
                        <el-col :xs="24" :sm="24" :md="24" :lg="6">
                            <el-form-item prop="teammatesName1" :label="$t('m.TeammatesName')" required>
                                <el-input
                                    v-model="signUpForm.teammatesName1"
                                    :placeholder="$t('m.TeammatesName')"
                                    width="100%" maxlength="6"
                                ></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="24" :lg="6">
                            <el-form-item prop="studentClass1" :label="$t('m.StudentClass')" required>
                                <el-input
                                    v-model="signUpForm.studentClass1"
                                    :placeholder="$t('m.StudentClass')"
                                    width="100%" maxlength="10"
                                ></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="24" :lg="6">
                            <el-form-item prop="studentId1" :label="$t('m.StudentId')" required>
                                <el-input
                                    v-model="signUpForm.studentId1"
                                    :placeholder="$t('m.StudentId')"
                                    width="100%" maxlength="10"
                                ></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="24" :lg="5">
                            <el-form-item prop="nickname1" :label="$t('m.StudentNickName')">
                                <el-input
                                    v-model="signUpForm.nickname1"
                                    :placeholder="$t('m.StudentNickName')"
                                    width="100%" maxlength="16"
                                ></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="24" :lg="6">
                            <el-form-item prop="usersName1" :label="$t('m.UsersName')" required>
                                <el-input
                                    v-model="signUpForm.usersName1"
                                    :placeholder="$t('m.UsersName')"
                                    :disabled="true"
                                    width="100%"
                                ></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="24" :lg="6">
                            <el-form-item prop="phoneNumber1" :label="$t('m.PhoneNumber')" required>
                                <el-input
                                    v-model="signUpForm.phoneNumber1"
                                    :placeholder="$t('m.PhoneNumber')"
                                    width="100%" maxlength="11"
                                ></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="24" :lg="6">
                            <el-form-item prop="size1" :label="$t('m.Size')" required>
                                <el-input
                                    v-model="signUpForm.size1"
                                    :placeholder="$t('m.Size')+'S/M/L'"
                                    width="100%" maxlength="10"
                                ></el-input>
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
                                    width="100%" maxlength="6"
                                ></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="24" :lg="6">
                            <el-form-item prop="studentClass2" :label="$t('m.StudentClass')" required>
                                <el-input
                                    v-model="signUpForm.studentClass2"
                                    :placeholder="$t('m.StudentClass')"
                                    width="100%" maxlength="10"
                                ></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="24" :lg="6">
                            <el-form-item prop="studentId2" :label="$t('m.StudentId')" required>
                                <el-input
                                    v-model="signUpForm.studentId2"
                                    :placeholder="$t('m.StudentId')"
                                    width="100%" maxlength="10"
                                ></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="24" :lg="5">
                            <el-form-item prop="nickname2" :label="$t('m.StudentNickName')" >
                                <el-input
                                    v-model="signUpForm.nickname2"
                                    :placeholder="$t('m.StudentNickName')"
                                    width="100%" maxlength="16"
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
                                    width="70%" maxlength="11"
                                ></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="24" :lg="6">
                            <el-form-item prop="size2" :label="$t('m.Size')" required>
                                <el-input
                                    v-model="signUpForm.size2"
                                    :placeholder="$t('m.Size')+'S/M/L'"
                                    width="100%" maxlength="10"
                                ></el-input>
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
                                    width="100%" maxlength="6"
                                ></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="24" :lg="6">
                            <el-form-item prop="studentClass3" :label="$t('m.StudentClass')" required>
                                <el-input
                                    v-model="signUpForm.studentClass3"
                                    :placeholder="$t('m.StudentClass')"
                                    width="100%" maxlength="10"
                                ></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="24" :lg="6">
                            <el-form-item prop="studentId3" :label="$t('m.StudentId')" required>
                                <el-input
                                    v-model="signUpForm.studentId3"
                                    :placeholder="$t('m.StudentId')"
                                    width="100%" maxlength="10"
                                ></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="24" :lg="5">
                            <el-form-item prop="nickname3" :label="$t('m.StudentNickName')">
                                <el-input
                                    v-model="signUpForm.nickname3"
                                    :placeholder="$t('m.StudentNickName')"
                                    width="100%" maxlength="16"
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
                                    width="70%" maxlength="11"
                                ></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="24" :lg="6">
                            <el-form-item prop="size3" :label="$t('m.Size')" required>
                                <el-input
                                    v-model="signUpForm.size3"
                                    :placeholder="$t('m.Size')+'S/M/L'"
                                    width="100%" maxlength="10"
                                ></el-input> 
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
                    <el-row>
                        <el-col :span="24" style="text-align: center;"> 
                            <el-button  style="width: 20rem;"
                                type="primary"
                                :loading="btnSignUpLoading"
                                @click="handleSignUp()"
                            >{{ $t('m.Sign_Up_Btn') }}</el-button>
                        </el-col>
                    </el-row>
                </el-card>
            </el-col>
        </el-row>
        <div v-if="isSignUp==true" id="signUpInfo">
            <div v-for="(signUpItem, index) in signUpItems" :key="index">
            <div style="text-align: center;font-size: 1rem;color: rgb(147, 145, 145);letter-spacing: 5px;">
                / {{ $t('m.SignItem' ) }}{{ index+1 }} /</div><br>
            <el-row  type="flex" justify="space-around">
                <el-col :span="24">
                    <el-card shadow>
                        <div slot="header">
                            <span class="panel-title">
                                <el-tag :type="SIGN_UP_STATUS_REVERSE[signUpItem.status]['type']" >
                                    {{ $t('m.' + SIGN_UP_STATUS_REVERSE[signUpItem.status]['name']) }}
                                </el-tag>
                            </span>
                        </div>
                        <el-form
                            :model="signUpItem"
                            ref="signUpItem"
                            label-width="100px"
                            :rules="rules"
                        >
                        <p id="teamInfo">{{$t('m.TeamInfo')}} </p>
                        <el-row>
                            <el-col :xs="24" :sm="24" :md="24" :lg="6">
                                <el-form-item prop="teamName" :label="$t('m.TeamName')" required>
                                    <el-input
                                        v-model="signUpItem.teamName"
                                        prefix-icon="el-icon-s-flag"
                                        :placeholder="$t('m.TeamName')"
                                        width="100%" maxlength="20"
                                    ></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :xs="24" :sm="24" :md="24" :lg="6">
                                <el-form-item prop="englishTeamName" :label="$t('m.EnglishTeamName')" required>
                                    <el-input
                                        v-model="signUpItem.englishTeamName"
                                        :placeholder="$t('m.EnglishTeamName')"
                                        width="100%" maxlength="25"
                                    ></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :xs="24" :sm="24" :md="24" :lg="6">
                                <el-form-item prop="school" :label="$t('m.School')">
                                    <el-input
                                        v-model="signUpItem.school"
                                        :placeholder="$t('m.School')"
                                        width="100%" maxlength="17"
                                    ></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :xs="24" :sm="24" :md="24" :lg="6">
                                <el-form-item prop="Star" :label="$t('m.IsStar')">
                                    <el-radio v-model="signUpItem.star" label="0">{{$t('m.Official')}}</el-radio>
                                    <el-radio v-model="signUpItem.star" label="1">{{$t('m.Star')}}</el-radio>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <p id="teamInfo">
                            <span style="margin-right: 10px;">{{$t('m.MemberInfo')}}</span>
                            <!-- <el-button v-if="signUpItem.captain==1" style="position: relative;margin-left: 10px;" type="primary" size="small" plain  @click="addMember_t(signUpItem.memberNum,index)"> -->
                            <el-button  style="position: relative;margin-left: 10px;" type="primary" size="small" plain  @click="addMember_t(signUpItem.memberNum,index)">
                                {{$t('m.AddMember')}}</el-button>
                            <!-- <el-button v-if="signUpItem.captain==1" type="primary" size="small" plain 
                                v-show="signUpItem.memberNum>1"      
                                @click="subMember_t(signUpItem.memberNum,index)"> -->
                            <el-button type="primary" size="small" plain 
                                v-show="signUpItem.memberNum>1"      
                                @click="subMember_t(signUpItem.memberNum,index)">
                                {{$t('m.SubMember')}}</el-button>
                        </p>
                        <el-row>
                        <p v-if="signUpItem.captain==0" id="memberInfo">{{$t('m.MemberOne')}}</p>
                        <p v-else>
                            <span style="color:#409eff;
                                font-size: 0.8rem; letter-spacing: 5px; font-weight: 600;">{{ $t('m.IsCaptain') }}
                            </span>
                        </p>
                            <el-col :xs="24" :sm="24" :md="24" :lg="6">
                                <el-form-item prop="teammatesName1" :label="$t('m.TeammatesName')" required>
                                    <el-input
                                        v-model="signUpItem.teammatesName1"
                                        :placeholder="$t('m.TeammatesName')"
                                        width="100%" maxlength="6"
                                    ></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :xs="24" :sm="24" :md="24" :lg="6">
                                <el-form-item prop="studentClass1" :label="$t('m.StudentClass')" required>
                                    <el-input
                                        v-model="signUpItem.studentClass1"
                                        :placeholder="$t('m.StudentClass')"
                                        width="100%" maxlength="10"
                                    ></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :xs="24" :sm="24" :md="24" :lg="6">
                                <el-form-item prop="studentId1" :label="$t('m.StudentId')" required>
                                    <el-input
                                        v-model="signUpItem.studentId1"
                                        :placeholder="$t('m.StudentId')"
                                        width="100%" maxlength="10"
                                    ></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :xs="24" :sm="24" :md="24" :lg="5">
                                <el-form-item prop="nickname1" :label="$t('m.StudentNickName')">
                                    <el-input
                                        v-model="signUpItem.nickname1"
                                        :placeholder="$t('m.StudentNickName')"
                                        width="100%" maxlength="16"
                                    ></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :xs="24" :sm="24" :md="24" :lg="6">
                                <el-form-item prop="usersName1" :label="$t('m.UsersName')" required>
                                    <el-input
                                        v-model="signUpItem.usersName1"
                                        :placeholder="$t('m.UsersName')"
                                        :disabled="true"
                                        width="100%"
                                    ></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :xs="24" :sm="24" :md="24" :lg="6">
                                <el-form-item prop="phoneNumber1" :label="$t('m.PhoneNumber')" required>
                                    <el-input
                                        v-model="signUpItem.phoneNumber1"
                                        :placeholder="$t('m.PhoneNumber')"
                                        width="100%" maxlength="11"
                                    ></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :xs="24" :sm="24" :md="24" :lg="6">
                                <el-form-item prop="size1" :label="$t('m.Size')" required>
                                    <el-input
                                        v-model="signUpItem.size1"
                                        :placeholder="$t('m.Size')+'S/M/L'"
                                        width="100%" maxlength="10"
                                    ></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :xs="24" :sm="24" :md="24" :lg="6">
                                <el-form-item prop="gender1" :label="$t('m.Gender')" required>
                                    <el-radio v-model="signUpItem.gender1" label="1">{{$t('m.Female')}}</el-radio>
                                    <el-radio v-model="signUpItem.gender1" label="0">{{$t('m.Male')}}</el-radio>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row v-if="signUpItem.memberNum>1">
                            <p id="memberInfo">{{$t('m.MemberTwo')}}</p>
                            <el-col :xs="24" :sm="24" :md="24" :lg="6">
                                <el-form-item prop="teammatesName2" :label="$t('m.TeammatesName')" required>
                                    <el-input
                                        v-model="signUpItem.teammatesName2"
                                        :placeholder="$t('m.TeammatesName')"
                                        width="100%" maxlength="6"
                                    ></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :xs="24" :sm="24" :md="24" :lg="6">
                                <el-form-item prop="studentClass2" :label="$t('m.StudentClass')" required>
                                    <el-input
                                        v-model="signUpItem.studentClass2"
                                        :placeholder="$t('m.StudentClass')"
                                        width="100%" maxlength="10"
                                    ></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :xs="24" :sm="24" :md="24" :lg="6">
                                <el-form-item prop="studentId2" :label="$t('m.StudentId')" required>
                                    <el-input
                                        v-model="signUpItem.studentId2"
                                        :placeholder="$t('m.StudentId')"
                                        width="100%" maxlength="10"
                                    ></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :xs="24" :sm="24" :md="24" :lg="5">
                                <el-form-item prop="nickname2" :label="$t('m.StudentNickName')" >
                                    <el-input
                                        v-model="signUpItem.nickname2"
                                        :placeholder="$t('m.StudentNickName')"
                                        width="100%" maxlength="16"
                                    ></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :xs="24" :sm="24" :md="24" :lg="6">
                                <el-form-item prop="usersName2" :label="$t('m.UsersName')" required>
                                    <el-input
                                        v-model="signUpItem.usersName2"
                                        :placeholder="$t('m.UsersName')"
                                        width="100%"
                                    ></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :xs="24" :sm="24" :md="24" :lg="6">
                                <el-form-item prop="phoneNumber2" :label="$t('m.PhoneNumber')" required>
                                    <el-input
                                        v-model="signUpItem.phoneNumber2"
                                        :placeholder="$t('m.PhoneNumber')"
                                        width="70%" maxlength="11"
                                    ></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :xs="24" :sm="24" :md="24" :lg="6">
                                <el-form-item prop="size2" :label="$t('m.Size')" required>
                                    <el-input
                                        v-model="signUpItem.size2"
                                        :placeholder="$t('m.Size')+'S/M/L'"
                                        width="100%" maxlength="10"
                                    ></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :xs="24" :sm="24" :md="24" :lg="6">
                                <el-form-item prop="gender2" :label="$t('m.Gender')" required>
                                    <el-radio v-model="signUpItem.gender2" label="1">{{$t('m.Female')}}</el-radio>
                                    <el-radio v-model="signUpItem.gender2" label="0">{{$t('m.Male')}}</el-radio>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row v-if="signUpItem.memberNum===3">
                            <p id="memberInfo">{{$t('m.MemberThree')}}</p>
                            <el-col :xs="24" :sm="24" :md="24" :lg="6">
                                <el-form-item prop="teammatesName3" :label="$t('m.TeammatesName')" required>
                                    <el-input
                                        v-model="signUpItem.teammatesName3"
                                        :placeholder="$t('m.TeammatesName')"
                                        width="100%" maxlength="6"
                                    ></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :xs="24" :sm="24" :md="24" :lg="6">
                                <el-form-item prop="studentClass3" :label="$t('m.StudentClass')" required>
                                    <el-input
                                        v-model="signUpItem.studentClass3"
                                        :placeholder="$t('m.StudentClass')"
                                        width="100%" maxlength="10"
                                    ></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :xs="24" :sm="24" :md="24" :lg="6">
                                <el-form-item prop="studentId3" :label="$t('m.StudentId')" required>
                                    <el-input
                                        v-model="signUpItem.studentId3"
                                        :placeholder="$t('m.StudentId')"
                                        width="100%" maxlength="10"
                                    ></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :xs="24" :sm="24" :md="24" :lg="5">
                                <el-form-item prop="nickname3" :label="$t('m.StudentNickName')">
                                    <el-input
                                        v-model="signUpItem.nickname3"
                                        :placeholder="$t('m.StudentNickName')"
                                        width="100%" maxlength="16"
                                    ></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :xs="24" :sm="24" :md="24" :lg="6">
                                <el-form-item prop="usersName3" :label="$t('m.UsersName')" required>
                                    <el-input
                                        v-model="signUpItem.usersName3"
                                        :placeholder="$t('m.UsersName')"
                                        width="100%"
                                    ></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :xs="24" :sm="24" :md="24" :lg="6">
                                <el-form-item prop="phoneNumber3" :label="$t('m.PhoneNumber')" required>
                                    <el-input
                                        v-model="signUpItem.phoneNumber3"
                                        :placeholder="$t('m.PhoneNumber')"
                                        width="70%" maxlength="11"
                                    ></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :xs="24" :sm="24" :md="24" :lg="6">
                                <el-form-item prop="size3" :label="$t('m.Size')" required>
                                    <el-input
                                        v-model="signUpItem.size3"
                                        :placeholder="$t('m.Size')+'S/M/L'"
                                        width="100%" maxlength="10"
                                    ></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :xs="24" :sm="24" :md="24" :lg="6">
                                <el-form-item prop="gender3" :label="$t('m.Gender')" required>
                                    <el-radio v-model="signUpItem.gender3" label="1">{{$t('m.Female')}}</el-radio>
                                    <el-radio v-model="signUpItem.gender3" label="0">{{$t('m.Male')}}</el-radio>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        </el-form>
                        <el-row>
                        <!-- <el-row v-if="signUpItem.captain==1"> -->
                            <el-col :span="24" style="text-align: center;"> 
                                <el-button  style="width: 20rem;"
                                    type="primary"
                                    :loading="btnSignUpLoading"
                                    @click="handleModifySignUp(signUpItem)"
                                >{{ $t('m.Sign_Up_Modify_Btn') }}</el-button>
                            </el-col>
                        </el-row>
                    </el-card>
                    <br>
                </el-col>
            </el-row>
            </div>

        </div>
    </div>
</template>
<script>
import api from '@/common/api';
import mMessage from '@/common/message';
import storage from "@/common/storage";
import {
  SIGN_UP_STATUS_REVERSE,
} from '@/common/constants';
export default {
  data() {
    return {
        isSignUp:false,
        signStatus:'',
        teamId:'',
        SIGN_UP_STATUS_REVERSE:{},
        signUpContestId:this.$route.params.signUpID,
        btnSignUpLoading:false,
        memberNum:1,
        maxParticipants:1,
        dialogVisible: false,
        signUpForm:{
            // 队伍信息
            teamName:'', 
            englishTeamName:'',
            star:'0',
            school:'西南科技大学',
            // 队长
            teammatesName1:'',
            studentClass1:'',
            studentId1:'',
            nickname1:'',
            usersName1:'',
            phoneNumber1:'',
            size1:'',
            gender1:'',
            // 队员二
            teammatesName2:'',
            studentClass2:'',
            studentId2:'',
            nickname2:'',
            usersName2:'',
            phoneNumber2:'',
            size2:'',
            gender2:'',
            // 队员三
            teammatesName3:'',
            studentClass3:'',
            studentId3:'',
            nickname3:'',
            usersName3:'',
            phoneNumber3:'',
            size3:'',
            gender3:'',
        },
        signUpItems:[],
        modifyMark:'',
        teammatesName:'',
        studentClass:'',
        studentId:'',
        nickname:'',
        usersName:'',
        phoneNumber:'',
        size:'',
        gender:'',
        rules:{
            teamName:[
                { required: true, message: this.$i18n.t('m.TeamName_Check_Required'), trigger: 'blur' },
                { min: 1, max: 15, message: '长度在 1 到 15 个字符', trigger: 'blur' }
            ],
            englishTeamName:[
                { required: true, message: this.$i18n.t('m.English_TeamName_Check_Required'), trigger: 'blur'},
                {  min: 1, max: 25, message: '长度在 1 到 25 个英文字符', trigger: 'blur' }
            ],
            teammatesName1:[
                { required: true, message: this.$i18n.t('m.TeammatesName_Check_Required'), trigger: 'blur', },
                { pattern: /^[\u4e00-\u9fa5]+$/ , min: 2, max: 6, message: '长度在 2 到 6 个中文字符', trigger: 'blur' }
            ],
            studentClass1:[
                { required: true, message: this.$i18n.t('m.StudentClass_Check_Required'), trigger: 'blur' },
                { min: 6 , message: '请填写正确班级格式', trigger: 'blur' }

            ],
            studentId1:[
                { required: true, message: this.$i18n.t('m.StudentId_Check_Required'), trigger: 'blur' },
            ],
            usersName1:[
                { required: true, message: this.$i18n.t('m.UsersName_Check_Required'), trigger: 'blur' },
            ],
            phoneNumber1:[
                { required: true, message: this.$i18n.t('m.PhoneNumber_Check_Required'), trigger: 'blur' },
                {  min: 11,
                    pattern: /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/,
                message: '请填写长度为11位且格式正确的电话号码' , trigger:'blur'}
            ],
            size1:[
                { required: true, message: this.$i18n.t('m.Size_Check_Required'), trigger: 'blur' },
                { pattern: /[a-zA-z]$/ , message: '请填写英文', trigger: 'blur' }
            ],
            gender1:[
                { required: true, message: this.$i18n.t('m.Gender_Check_Required'), trigger: 'blur' },
            ],
            teammatesName2:[
                { required: true, message: this.$i18n.t('m.TeammatesName_Check_Required'), trigger: 'blur' },
                { pattern: /^[\u4e00-\u9fa5]+$/ , min: 2, max: 6, message: '长度在 2 到 6 个中文字符', trigger: 'blur' }
            ],
            studentClass2:[
                { required: true, message: this.$i18n.t('m.StudentClass_Check_Required'), trigger: 'blur' },
                { min: 6 , message: '请填写正确班级格式', trigger: 'blur' }
            ],
            studentId2:[
                { required: true, message: this.$i18n.t('m.StudentId_Check_Required'), trigger: 'blur' },
            ],
            usersName2:[
                { required: true, message: this.$i18n.t('m.UsersName_Check_Required'), trigger: 'blur' },
            ],
            phoneNumber2:[
                { required: true, message: this.$i18n.t('m.PhoneNumber_Check_Required'), trigger: 'blur' },
                {  min: 11,
                    pattern: /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/ ,
                message: '请填写长度为11位且格式正确的电话号码' , trigger:'blur'}
            ],
            size2:[
                { required: true, message: this.$i18n.t('m.Size_Check_Required'), trigger: 'blur' },
                { pattern: /[a-zA-z]$/ , message: '请填写英文', trigger: 'blur' }
            ],
            gender2:[
                { required: true, message: this.$i18n.t('m.Gender_Check_Required'), trigger: 'blur' },
            ],
            teammatesName3:[
                { required: true, message: this.$i18n.t('m.TeammatesName_Check_Required'), trigger: 'blur' },
                { pattern: /^[\u4e00-\u9fa5]+$/ , min: 2, max: 6, message: '长度在 2 到 6 个中文字符', trigger: 'blur' }
            ],
            studentClass3:[
                { required: true, message: this.$i18n.t('m.StudentClass_Check_Required'), trigger: 'blur' },
                { min: 6 , message: '请填写正确班级格式', trigger: 'blur' }
            ],
            studentId3:[
                { required: true, message: this.$i18n.t('m.StudentId_Check_Required'), trigger: 'blur' },
            ],
            usersName3:[
                { required: true, message: this.$i18n.t('m.UsersName_Check_Required'), trigger: 'blur' },
            ],
            phoneNumber3:[
                { required: true, message: this.$i18n.t('m.PhoneNumber_Check_Required'), trigger: 'blur' },
                { min: 11,
                    pattern: /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/ ,
                    message: '请填写长度为11位且格式正确的电话号码' , trigger:'blur'}
            ],
            size3:[
                { required: true, message: this.$i18n.t('m.Size_Check_Required'), trigger: 'blur' },
                { pattern: /[a-zA-z]$/ , message: '请填写英文', trigger: 'blur' }
            ],
            gender3:[
                { required: true, message: this.$i18n.t('m.Gender_Check_Required'), trigger: 'blur' },
            ],
        }
    }
  },
  methods:{
    handleSignUp() {   

        if (!this.signUpForm.teamName) {
            mMessage.error( this.$i18n.t("m.TeamName_Check_Required") );
            return;
        }
        if (!this.signUpForm.englishTeamName) {
            mMessage.error( this.$i18n.t("m.English_TeamName_Check_Required") );
            return;
        }
        if(this.memberNum==1){
            if(!this.signUpForm.teammatesName1){
                mMessage.error( this.$i18n.t("m.TeammatesName_Check_Required") );
                return;
            }
            if(!this.signUpForm.studentClass1){
                mMessage.error( this.$i18n.t("m.StudentClass_Check_Required") );
                return;
            }
            if(!this.signUpForm.studentId1){
                mMessage.error( this.$i18n.t("m.StudentId_Check_Required") );
                return;
            }
            if(!this.signUpForm.usersName1){
                mMessage.error( this.$i18n.t("m.UsersName_Check_Required") );
                return;
            }
            if(!this.signUpForm.phoneNumber1&&this.signUpForm.phoneNumber1.length<11){
                mMessage.error( this.$i18n.t("m.PhoneNumber_Check_Required") );
                return;
            }
            if(!this.signUpForm.size1){
                mMessage.error( this.$i18n.t("m.Size_Check_Required") );
                return;
            }
            if(!this.signUpForm.gender1){
                mMessage.error( this.$i18n.t("m.Gender_Check_Required") );
                return;
            }
        }
        if(this.memberNum>1){
            if(!this.signUpForm.teammatesName2){
                mMessage.error( this.$i18n.t("m.TeammatesName_Check_Required") );
                return;
            }
            if(!this.signUpForm.studentClass2){
                mMessage.error( this.$i18n.t("m.StudentClass_Check_Required") );
                return;
            }
            if(!this.signUpForm.studentId2){
                mMessage.error( this.$i18n.t("m.StudentId_Check_Required") );
                return;
            }
            if(!this.signUpForm.usersName2){
                mMessage.error( this.$i18n.t("m.UsersName_Check_Required") );
                return;
            }
            if(this.signUpForm.phoneNumber2.length<11){
                mMessage.error( this.$i18n.t("m.PhoneNumber_Check_Required") );
                return;
            }
            if(!this.signUpForm.size2){
                mMessage.error( this.$i18n.t("m.Size_Check_Required") );
                return;
            }
            if(!this.signUpForm.gender2){
                mMessage.error( this.$i18n.t("m.Gender_Check_Required") );
                return;
            }            
        }
        if(this.memberNum>2){
            if(!this.signUpForm.teammatesName3){
                mMessage.error( this.$i18n.t("m.TeammatesName_Check_Required") );
                return;
            }
            if(!this.signUpForm.studentClass3){
                mMessage.error( this.$i18n.t("m.StudentClass_Check_Required") );
                return;
            }
            if(!this.signUpForm.studentId3){
                mMessage.error( this.$i18n.t("m.StudentId_Check_Required") );
                return;
            }
            if(!this.signUpForm.usersName3){
                mMessage.error( this.$i18n.t("m.UsersName_Check_Required") );
                return;
            }
            if(this.signUpForm.phoneNumber3.length<11){
                mMessage.error( this.$i18n.t("m.PhoneNumber_Check_Required") );
                return;
            }
            if(!this.signUpForm.size3){
                mMessage.error( this.$i18n.t("m.Size_Check_Required") );
                return;
            }
            if(!this.signUpForm.gender3){
                mMessage.error( this.$i18n.t("m.Gender_Check_Required") );
                return;
            }
        }       

        this.teammatesName = this.signUpForm.teammatesName1 + '-' + 
                            (this.signUpForm.teammatesName2 == '' ?'*':this.signUpForm.teammatesName2) + '-' + 
                            (this.signUpForm.teammatesName3 == '' ?'*':this.signUpForm.teammatesName3)
        this.studentClass = this.signUpForm.studentClass1 + '-' + 
                            (this.signUpForm.studentClass2 == '' ?'*':this.signUpForm.studentClass2) + '-' + 
                            (this.signUpForm.studentClass3 == '' ?'*':this.signUpForm.studentClass3)
        this.studentId = this.signUpForm.studentId1 + '-' + 
                            (this.signUpForm.studentId2 == '' ?'*':this.signUpForm.studentId2) + '-' + 
                            (this.signUpForm.studentId3 == '' ?'*':this.signUpForm.studentId3)
        this.nickname = (this.signUpForm.nickname1 == '' ?'*':this.signUpForm.nickname1) + '-' + 
                            (this.signUpForm.nickname2 == '' ?'*':this.signUpForm.nickname2) + '-' + 
                            (this.signUpForm.nickname3 == '' ?'*':this.signUpForm.nickname3)
        this.usersName = this.signUpForm.usersName1 + '-' + 
                            (this.signUpForm.usersName2 == '' ?'*':this.signUpForm.usersName2) + '-' + 
                            (this.signUpForm.usersName3 == '' ?'*':this.signUpForm.usersName3)
        this.phoneNumber = this.signUpForm.phoneNumber1 + '-' + 
                            (this.signUpForm.phoneNumber2 == '' ?'*':this.signUpForm.phoneNumber2) + '-' + 
                            (this.signUpForm.phoneNumber3 == '' ?'*':this.signUpForm.phoneNumber3)
        this.size = this.signUpForm.size1 + '-' + 
                            (this.signUpForm.size2 == '' ?'*':this.signUpForm.size2) + '-' + 
                            (this.signUpForm.size3 == '' ?'*':this.signUpForm.size3)
        this.gender = this.signUpForm.gender1 + '-' + 
                            (this.signUpForm.gender2 == '' ?'*':this.signUpForm.gender2) + '-' + 
                            (this.signUpForm.gender3 == '' ?'*':this.signUpForm.gender3)
        let formData = {
            signUpContestId:this.signUpContestId,
            teamName:this.signUpForm.teamName, 
            englishTeamName:this.signUpForm.englishTeamName,
            star:this.signUpForm.star,
            school:this.signUpForm.school,
            teammatesName:this.teammatesName,
            studentClass:this.studentClass,
            studentId:this.studentId,
            nickname:this.nickname,
            usersName:this.usersName,
            phoneNumber:this.phoneNumber,
            size:this.size,
            gender:this.gender
        }
        this.$refs['signUpForm'].validate((valid) => {
        if (valid) {
          this.btnSignUpLoading = true;
          if(this.signStatus==200){
            formData.status = 200
            formData.id = this.teamId
            api.applyForUpdateTeamInfo(formData).then(
                (res) => {
                    this.btnSignUpLoading = false;
                    if(res.data.status==200){
                        mMessage.success(this.$i18n.t('m.Sign_up_Ac_Modify_success'));
                        this.$router.push({
                            name: 'SignUpDetails',
                            params: { signUpID: this.signUpContestId },
                        });
                    }
                }
            )
          }
          else{
            if(this.signStatus===''){
                api.addSignUpInfo(formData).then(
                    (res) => {
                    this.btnSignUpLoading = false;
                    if(res.data.status==200){
                        mMessage.success(this.$i18n.t('m.Sign_up_Success'));
                        this.$router.push({
                            name: 'SignUpDetails',
                            params: { signUpID: this.signUpContestId },
                        });
                    }
                    },
                    (err) =>{
                    this.btnSignUpLoading = false;
                    }
                );
            }
            else{
                formData.status = this.signStatus
                formData.id = this.teamId
                api.putTeamInfo(formData).then(
                    (res)=>{
                        if(res.data.status==200){
                            mMessage.success(this.$i18n.t('m.Sign_up_Modify_Success')); 
                            this.$router.push({
                                name: 'SignUpDetails',
                                params: { signUpID: this.signUpContestId },
                            });
                        }
                    }
                )
            }
          }
          this.btnSignUpLoading = false;
        }
      });
    },
    handleModifySignUp(signUpForm){
        if (!signUpForm.teamName) {
            mMessage.error( this.$i18n.t("m.TeamName_Check_Required") );
            return;
        }
        if (!signUpForm.englishTeamName) {
            mMessage.error( this.$i18n.t("m.English_TeamName_Check_Required") );
            return;
        }
        if(signUpForm.memberNum>0){
            if(!signUpForm.teammatesName1){
                mMessage.error( this.$i18n.t("m.TeammatesName_Check_Required") );
                return;
            }
            if(!signUpForm.studentClass1){
                mMessage.error( this.$i18n.t("m.StudentClass_Check_Required") );
                return;
            }
            if(!signUpForm.studentId1){
                mMessage.error( this.$i18n.t("m.StudentId_Check_Required") );
                return;
            }
            if(!signUpForm.usersName1){
                mMessage.error( this.$i18n.t("m.UsersName_Check_Required") );
                return;
            }
            if(signUpForm.phoneNumber1.length<11){
                mMessage.error( this.$i18n.t("m.PhoneNumber_Check_Required") );
                return;
            }
            if(!signUpForm.size1){
                mMessage.error( this.$i18n.t("m.Size_Check_Required") );
                return;
            }
            if(!signUpForm.gender1){
                mMessage.error( this.$i18n.t("m.Gender_Check_Required") );
                return;
            }
        }
        if(signUpForm.memberNum>1){
            if(!signUpForm.teammatesName2){
                mMessage.error( this.$i18n.t("m.TeammatesName_Check_Required") );
                return;
            }
            if(!signUpForm.studentClass2){
                mMessage.error( this.$i18n.t("m.StudentClass_Check_Required") );
                return;
            }
            if(!signUpForm.studentId2){
                mMessage.error( this.$i18n.t("m.StudentId_Check_Required") );
                return;
            }
            if(!signUpForm.usersName2){
                mMessage.error( this.$i18n.t("m.UsersName_Check_Required") );
                return;
            }
            if(signUpForm.phoneNumber2.length<11){
                mMessage.error( this.$i18n.t("m.PhoneNumber_Check_Required") );
                return;
            }
            if(!signUpForm.size2){
                mMessage.error( this.$i18n.t("m.Size_Check_Required") );
                return;
            }
            if(!signUpForm.gender2){
                mMessage.error( this.$i18n.t("m.Gender_Check_Required") );
                return;
            }            
        }
        if(signUpForm.memberNum>2){
            if(!signUpForm.teammatesName3){
                mMessage.error( this.$i18n.t("m.TeammatesName_Check_Required") );
                return;
            }
            if(!signUpForm.studentClass3){
                mMessage.error( this.$i18n.t("m.StudentClass_Check_Required") );
                return;
            }
            if(!signUpForm.studentId3){
                mMessage.error( this.$i18n.t("m.StudentId_Check_Required") );
                return;
            }
            if(!signUpForm.usersName3){
                mMessage.error( this.$i18n.t("m.UsersName_Check_Required") );
                return;
            }
            if(signUpForm.phoneNumber3.length<11){
                mMessage.error( this.$i18n.t("m.PhoneNumber_Check_Required") );
                return;
            }
            if(!signUpForm.size3){
                mMessage.error( this.$i18n.t("m.Size_Check_Required") );
                return;
            }
            if(!signUpForm.gender3){
                mMessage.error( this.$i18n.t("m.Gender_Check_Required") );
                return;
            }
        }
        this.teammatesName = signUpForm.teammatesName1 + '-' + 
                            (signUpForm.teammatesName2 == '' ?'*':signUpForm.teammatesName2) + '-' + 
                            (signUpForm.teammatesName3 == '' ?'*':signUpForm.teammatesName3)
        this.studentClass = signUpForm.studentClass1 + '-' + 
                            (signUpForm.studentClass2 == '' ?'*':signUpForm.studentClass2) + '-' + 
                            (signUpForm.studentClass3 == '' ?'*':signUpForm.studentClass3)
        this.studentId = signUpForm.studentId1 + '-' + 
                            (signUpForm.studentId2 == '' ?'*':signUpForm.studentId2) + '-' + 
                            (signUpForm.studentId3 == '' ?'*':signUpForm.studentId3)
        this.nickname = (signUpForm.nickname1 == '' ?'*':signUpForm.nickname1) + '-' + 
                            (signUpForm.nickname2 == '' ?'*':signUpForm.nickname2) + '-' + 
                            (signUpForm.nickname3 == '' ?'*':signUpForm.nickname3)
        this.usersName = signUpForm.usersName1 + '-' + 
                            (signUpForm.usersName2 == '' ?'*':signUpForm.usersName2) + '-' + 
                            (signUpForm.usersName3 == '' ?'*':signUpForm.usersName3)
        this.phoneNumber = signUpForm.phoneNumber1 + '-' + 
                            (signUpForm.phoneNumber2 == '' ?'*':signUpForm.phoneNumber2) + '-' + 
                            (signUpForm.phoneNumber3 == '' ?'*':signUpForm.phoneNumber3)
        this.size = signUpForm.size1 + '-' + 
                            (signUpForm.size2 == '' ?'*':signUpForm.size2) + '-' + 
                            (signUpForm.size3 == '' ?'*':signUpForm.size3)
        this.gender = signUpForm.gender1 + '-' + 
                            (signUpForm.gender2 == '' ?'*':signUpForm.gender2) + '-' + 
                            (signUpForm.gender3 == '' ?'*':signUpForm.gender3)
        let formData = {
            signUpContestId:this.signUpContestId,
            teamName:signUpForm.teamName, 
            englishTeamName:signUpForm.englishTeamName,
            star:signUpForm.star,
            school:signUpForm.school,
            teammatesName:this.teammatesName,
            studentClass:this.studentClass,
            studentId:this.studentId,
            nickname:this.nickname,
            usersName:this.usersName,
            phoneNumber:this.phoneNumber,
            size:this.size,
            gender:this.gender
        }
        this.btnSignUpLoading = true;
        if(signUpForm.status==200){
        formData.status = signUpForm.status
        formData.id = signUpForm.teamId
        api.applyForUpdateTeamInfo(formData).then(
            (res) => {
                this.btnSignUpLoading = false;
                if(res.data.status==200){
                    mMessage.success(this.$i18n.t('m.Sign_up_Ac_Modify_success'));
                    this.$router.push({
                        name: 'SignUpDetails',
                        params: { signUpID: this.signUpContestId },
                    });
                }else{
                    this.btnSignUpLoading = false;
                }
            }
        )
        }
        else{
        formData.status = signUpForm.status
        formData.id = signUpForm.teamId
        api.putTeamInfo(formData).then(
            (res)=>{
                if(res.data.status==200){
                this.btnSignUpLoading = true;
                    mMessage.success(this.$i18n.t('m.Sign_up_Modify_Success')); 
                    this.$router.push({
                        name: 'SignUpDetails',
                        params: { signUpID: this.signUpContestId }
                    });
                }else{
                    this.btnSignUpLoading = true;
                }
            }
        )
        }
        this.btnSignUpLoading = false;
    },
    addMember(){
        if(this.memberNum<this.maxParticipants){
            if(this.memberNum===3){
                mMessage.error(this.$i18n.t('m.CanNotAddMember'));
                this.memberNum =3;
            }
            else{
                this.memberNum +=1;
            }
        }else{
            mMessage.error(this.$i18n.t('m.CanNotAddMember'));
        }
    },
    subMember(){
        if(this.memberNum===1){
            // this.$refs.form.clearValidate()
        }
        else{
            this.memberNum -=1;
            if(this.memberNum===2){
                this.signUpForm.teammatesName3=''
                this.signUpForm.studentClass3 = ''
                this.signUpForm.studentId3 = ''
                this.signUpForm.nickname3 = ''
                this.signUpForm.usersName3 = ''
                this.signUpForm.phoneNumber3 = ''
                this.signUpForm.size3 = ''
                this.signUpForm.gender3 =''
            }
            if(this.memberNum===1){
                this.signUpForm.teammatesName2=''
                this.signUpForm.studentClass2 = ''
                this.signUpForm.studentId2 = ''
                this.signUpForm.nickname2 = ''
                this.signUpForm.usersName2 = ''
                this.signUpForm.phoneNumber2 = ''
                this.signUpForm.size2 = ''
                this.signUpForm.gender2 =''
            }
            // this.$refs.form.clearValidate()
        }
    },
    addMember_t(num,index){
        if(num<this.maxParticipants){
            if(num===3){
                mMessage.error(this.$i18n.t('m.CanNotAddMember'));
                this.signUpItems[index].memberNum =3;
            }
            else{
                this.signUpItems[index].memberNum +=1;
            }
        }else{
            mMessage.error(this.$i18n.t('m.CanNotAddMember'));
        }
    },
    subMember_t(num,index){
        if(num===1){
            this.$set(this.signUpItems[index],'memberNum',1)
        }
        else{
            this.signUpItems[index].memberNum-=1
            if(num===2){
                this.$set(this.signUpItems[index],'memberNum',1)
                this.signUpItems[index].teammatesName2=''
                this.signUpItems[index].studentClass2 = ''
                this.signUpItems[index].studentId2 = ''
                this.signUpItems[index].nickname2 = ''
                this.signUpItems[index].usersName2 = ''
                this.signUpItems[index].phoneNumber2 = ''
                this.signUpItems[index].size2 = ''
                this.signUpItems[index].gender2 =''
                this.$set(this.signUpItems[index],'teammatesName2','')
                this.$set(this.signUpItems[index],'studentClass2','')
                this.$set(this.signUpItems[index],'studentId2','')
                this.$set(this.signUpItems[index],'nickname2','')
                this.$set(this.signUpItems[index],'usersName2','')
                this.$set(this.signUpItems[index],'phoneNumber2','')
                this.$set(this.signUpItems[index],'size2','')
                this.$set(this.signUpItems[index],'gender2','')
            }
            if(num===3){
                this.$set(this.signUpItems[index],'memberNum',2)
                this.signUpItems[index].teammatesName3=''
                this.signUpItems[index].studentClass3 = ''
                this.signUpItems[index].studentId3 = ''
                this.signUpItems[index].nickname3 = ''
                this.signUpItems[index].usersName3 = ''
                this.signUpItems[index].phoneNumber3 = ''
                this.signUpItems[index].size3 = ''
                this.signUpItems[index].gender3 =''
                this.$set(this.signUpItems[index],'teammatesName3','')
                this.$set(this.signUpItems[index],'studentClass3','')
                this.$set(this.signUpItems[index],'studentId3','')
                this.$set(this.signUpItems[index],'nickname3','')
                this.$set(this.signUpItems[index],'usersName3','')
                this.$set(this.signUpItems[index],'phoneNumber3','')
                this.$set(this.signUpItems[index],'size3','')
                this.$set(this.signUpItems[index],'gender3','')
            }
            // this.$refs.form.clearValidate()
        }
    },
    getSignUpState(){
        let user = storage.get('userInfo')
        this.signUpForm.usersName1 = user.username
        api.getTeamInfoByUserName(this.signUpContestId).then(
            (res) => {
                if(res.data.status==200){
                    if(res.data.data!=''){
                        let listData = res.data.data
                        this.isSignUp=true
                        let signUpForm={}
                        for(let i in listData){
                            // this.signUpItems.push(listData[i])
                            signUpForm['teamName'] = listData[i].teamName
                            signUpForm['captain'] = listData[i].captain
                            signUpForm['englishTeamName'] = listData[i].englishTeamName
                            signUpForm['status'] = listData[i].status
                            signUpForm['teamId'] = listData[i].id
                            signUpForm['star'] = listData[i].star===0?'0':'1'
                            signUpForm['school'] = listData[i].school
                            signUpForm['teammatesName1'] = listData[i].teammatesName.split("-")[0]
                            signUpForm['teammatesName2'] = listData[i].teammatesName.split("-")[1]=='*'?'':listData[i].teammatesName.split("-")[1]
                            signUpForm['teammatesName3'] = listData[i].teammatesName.split("-")[2]=='*'?'':listData[i].teammatesName.split("-")[2]
                            signUpForm['studentClass1'] = listData[i].studentClass.split("-")[0]
                            signUpForm['studentClass2'] = listData[i].studentClass.split("-")[1]=='*'?'':listData[i].studentClass.split("-")[1]
                            signUpForm['studentClass3'] = listData[i].studentClass.split("-")[2]=='*'?'':listData[i].studentClass.split("-")[2]
                            signUpForm['studentId1'] = listData[i].studentId.split("-")[0]
                            signUpForm['studentId2'] = listData[i].studentId.split("-")[1]=='*'?'':listData[i].studentId.split("-")[1]
                            signUpForm['studentId3'] = listData[i].studentId.split("-")[2]=='*'?'':listData[i].studentId.split("-")[2]
                            signUpForm['nickname1'] = listData[i].nickname.split("-")[0]=='*'?'':listData[i].nickname.split("-")[0]
                            signUpForm['nickname2'] = listData[i].nickname.split("-")[1]=='*'?'':listData[i].nickname.split("-")[1]
                            signUpForm['nickname3'] = listData[i].nickname.split("-")[2]=='*'?'':listData[i].nickname.split("-")[2]
                            signUpForm['usersName1'] = listData[i].usersName.split("-")[0]
                            signUpForm['usersName2'] = listData[i].usersName.split("-")[1]=='*'?'':listData[i].usersName.split("-")[1]
                            signUpForm['usersName3'] = listData[i].usersName.split("-")[2]=='*'?'':listData[i].usersName.split("-")[2]
                            signUpForm['phoneNumber1'] = listData[i].phoneNumber.split("-")[0]
                            signUpForm['phoneNumber2'] = listData[i].phoneNumber.split("-")[1]=='*'?'':listData[i].phoneNumber.split("-")[1]
                            signUpForm['phoneNumber3'] = listData[i].phoneNumber.split("-")[2]=='*'?'':listData[i].phoneNumber.split("-")[2]
                            signUpForm['size1'] = listData[i].size.split("-")[0]
                            signUpForm['size2'] = listData[i].size.split("-")[1]=='*'?'':listData[i].size.split("-")[1]
                            signUpForm['size3'] = listData[i].size.split("-")[2]=='*'?'':listData[i].size.split("-")[2]
                            signUpForm['gender1'] = listData[i].gender.split("-")[0]
                            signUpForm['gender2'] = listData[i].gender.split("-")[1]=='*'?'':listData[i].gender.split("-")[1]
                            signUpForm['gender3'] = listData[i].gender.split("-")[2]=='*'?'':listData[i].gender.split("-")[2]
                            if(signUpForm['teammatesName2']!=''&&signUpForm['teammatesName3']=='')
                                signUpForm['memberNum']=2
                            else if(signUpForm['teammatesName3']!='')
                                signUpForm['memberNum']=3
                            else signUpForm['memberNum']=1
                            this.signUpItems.push(signUpForm);
                            signUpForm={}
                        }
                    }
                }
            },
        )
    },
    handleClose(done) {
        done()
        // this.$confirm('确认关闭？')
        //   .then(_ => {
        //     done();
        //   })
        //   .catch(_ => {});
    },
    isStar(){}
  },
  created(){
    this.getSignUpState()
  },
  mounted(){
    this.SIGN_UP_STATUS_REVERSE = Object.assign({}, SIGN_UP_STATUS_REVERSE);
    this.maxParticipants = storage.get('maxParticipants')
  }
}
</script>

<style>
#teamInfo{
    font-size: 1.1rem;
}
#memberInfo{
    font-size: 0.8rem;
}
</style>