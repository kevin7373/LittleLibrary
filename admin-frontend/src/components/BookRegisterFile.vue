<template>
  <v-container fluid grid-list-xl>
    <v-layout row wrap style="background-color: #f5f5f5">
      <v-flex xs12>
        <v-card>
          <v-card-title>
            여러 권 등록
            <v-spacer></v-spacer>
          </v-card-title>
          <v-divider></v-divider>
          <v-card-subtitle>파일 업로드</v-card-subtitle>
          <v-card-text>
            <v-divider v-if="$vuetify.breakpoint.mdAndUp"></v-divider>
            <v-flex d-flex>
              <v-file-input
                show-size
                accept=".xlsx"
                label="업로드할 파일을 선택하세요"
                outlined
                @change="onFileChange"
                type="file"
                hide-details="auto"
                single-line
                :error-messages="fileErrMsg"
              ></v-file-input>
            </v-flex>
            <v-flex class="pt-0 text-right">
              <v-btn
                color="green"
                dark
                class="ma-0"
                type="file"
                tile
                depressed
                @click.prevent="ReadXLSX(uploadFile)"
              >엑셀로 등록</v-btn>
            </v-flex>
            <v-flex xs12 v-if="fileList.length > 0">
              <v-simple-table>
                <template v-slot:default>
                  <thead>
                    <tr>
                      <th class="text-center">번호</th>
                      <th class="text-center">도서 아이디</th>
                      <th class="text-center">ISBN 코드</th>
                      <th class="text-center">카테고리</th>
                      <th class="text-center">지역</th>
                      <th class="text-center">반</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr v-for="(item, index) in fileList" :key="index">
                      <td class="text-center">{{ index + 1 }}</td>
                      <td class="text-center">{{ item.bookId }}</td>
                      <td class="text-center">{{ item.bookISBN }}</td>
                      <td class="text-center">{{ item.category }}</td>
                      <td class="text-center">{{ item.city }}</td>
                      <td class="text-center">{{ item.class }}</td>
                    </tr>
                  </tbody>
                </template>
              </v-simple-table>
            </v-flex>
            <v-flex xs12 v-if="remainList.length > 0">
              <v-simple-table>
                <template v-slot:default>
                  <thead>
                    <tr>
                      <th class="text-center">번호</th>
                      <th class="text-center">이유</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr v-for="(item, index) in remainList" :key="item">
                      <td class="text-center">{{ item }}</td>
                      <td class="text-center">{{ reasonList[index] }}</td>
                    </tr>
                  </tbody>
                </template>
              </v-simple-table>
            </v-flex>
          </v-card-text>
        </v-card>
      </v-flex>
      <v-flex class="pt-0 text-right">
        <v-btn color="#3396f4" dark depressed class="ma-0" @click="makeXLSX()">엑셀 양식 내려받기</v-btn>
      </v-flex>
      <v-dialog v-model="loading" fullscreen>
        <v-container fluid fill-height style="background-color: rgba(255, 255, 255, 0.5);">
          <v-layout justify-center align-center>
            <v-progress-circular indeterminate color="primary"></v-progress-circular>
          </v-layout>
        </v-container>
      </v-dialog>
    </v-layout>
  </v-container>
</template>

<script>
import { mapGetters } from "vuex";
import XLSX from "xlsx";

export default {
  name: "BookRegisterPage",
  computed: {
    ...mapGetters(["requestHeader", "user", "getAxios"])
  },
  data() {
    return {
      uploadFile: "",
      loading: false,
      fileList: [],
      remainList: [],
      reasonList: [],
      successList: [],
      sBidList: [],
      fileErrMsg: "",
      exportData: [["도서 아이디", "ISBN 코드", "카테고리", "지역", "반"]]
    };
  },
  methods: {
    goBack() {
      this.$router.go(-1);
    },
    onFileChange(e) {
      this.uploadFile = e;
      var files = e;

      this.fileList = [];
      this.remainList = [];
      this.reasonList = [];
      this.fileErrMsg = "";

      if (files == null || !files.size) {
        return;
      }
      var fileName = "";
      fileName = files["name"];

      var reg = /(.*?)\.(xlsx)$/;

      if (!fileName.match(reg)) {
        this.fileErrMsg = "xlsx 파일을 올려주세요";
        return;
      } else if (fileName.match(reg)) {
        this.fileErrMsg = "";
      }
      this.uploadFile = files;

      var reader = new FileReader();
      var vm = this;

      reader.onload = function() {
        var data = reader.result;
        var workbook = XLSX.read(data, { type: "binary" });
        var rowObj = XLSX.utils.sheet_to_json(
          workbook.Sheets[workbook.SheetNames[0]]
        );

        var obj = [];

        for (let i = 0; i < rowObj.length; i++) {
          var tmp = {};
          tmp.bookId = rowObj[i]["도서 아이디"];
          tmp.bookISBN = rowObj[i]["ISBN 코드"];
          tmp.category = rowObj[i]["카테고리"];
          tmp.city = rowObj[i]["지역"];
          tmp.class = rowObj[i]["반"];
          obj.push(tmp);
        }

        for (let i = 0; i < rowObj.length; i++) {
          vm.fileList.push(obj[i]);
        }
      };
      reader.readAsBinaryString(this.uploadFile);
    },
    ReadXLSX(file) {
      var reader = new FileReader();
      var vm = this;

      reader.onload = function() {
        var data = reader.result;
        var workbook = XLSX.read(data, { type: "binary" });
        workbook.SheetNames.forEach(function(sheetName) {
          var rowObj = XLSX.utils.sheet_to_json(workbook.Sheets[sheetName]);
          vm.sendObject(rowObj);
        });
      };

      if (file == null || !file.size) {
        this.fileErrMsg = "파일을 올려주세요";
        return;
      }
      reader.readAsBinaryString(file);
    },
    sendObject(book) {
      this.loading = true;
      this.getAxios
        .post(
          "/admin/book/registerFile",
          {
            book
          },
          this.requestHeader
        )
        .then(res => {
          this.uploadFile = "";
          this.loading = false;
          this.fileList = [];

          if (res.data.remainList.length > 0) {
            this.remainList = res.data.remainList;
            this.reasonList = res.data.reasonList;
          }
        })
        .catch(err => {
          console.log(err)
          this.loading = false;
          this.uploadFile = "";
          this.fileList = [];
          this.fileErrMsg = "파일을 확인해주세요";
        });
    },
    makeXLSX() {
      if (typeof XLSX == "undefined") {
        XLSX = require("xlsx");
      }
      var ws = XLSX.utils.aoa_to_sheet(this.exportData);
      var wb = XLSX.utils.book_new();
      XLSX.utils.book_append_sheet(wb, ws, "Sheet1");
      XLSX.writeFile(wb, "도서등록양식.xlsx");
    }
  },
  mounted() {}
};
</script>