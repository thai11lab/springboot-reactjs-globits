import React, { Component } from "react";
import {
  IconButton,
  Table,
  TableHead,
  TableBody,
  TableRow,
  TableCell,
  Icon,
  TablePagination,
  Button,
  Card,
  TextField,
  InputAdornment,
  Grid,
} from "@material-ui/core";

import { getAllEmployees, deleteUser,getAllEmployeesBySearch,getAllEmployeesBySearchExcell,getList} from "./EmployeeService1";
import MemberEditorDialog from "./MemberEditorDialog1";
import { Breadcrumb, ConfirmationDialog } from "egret";
import JwtAuthService from '../../services/jwtAuthService';
import shortid from "shortid";
import * as FileSaver from 'file-saver';
import * as XLSX from 'xlsx';
import { isThisSecond } from "date-fns";
class EmployeeTable extends Component {
  state = {
    rowsPerPage: 10,
    page: 0,
    userList: [],
    shouldOpenEditorDialog: false,
    shouldOpenConfirmationDialog: false,
    title:null,
    totalEmployee:0
  };

  setPage = page => {
    this.setState({ page },function(){
      if(this.state.title){
        this.handleSubmit();
      }else{
        this.updatePageData(this.state.page,this.state.rowsPerPage);
      }
    }
    );
      
  };

  setRowsPerPage = event => {
    this.setState({ rowsPerPage: event.target.value,page:0},function(){
       if(this.state.title){
        this.handleSubmit();
       }else{
        this.updatePageData(this.state.page,this.state.rowsPerPage);
       }
    }
     );
     
     
  };

  handleChangePage = (event, newPage) => {
    this.setPage(newPage);
  };


  handleDialogClose = () => {
    this.setState({
      shouldOpenEditorDialog: false,
      shouldOpenConfirmationDialog: false
    });
    this.updatePageData();
  };

  handleDeleteUser = user => {
    this.setState({
      user,
      shouldOpenConfirmationDialog: true
    });
  };

  handleConfirmationResponse = () => {
    deleteUser(this.state.user).then(() => {
      this.handleDialogClose();
    });
  };


  
  componentDidMount() {
    this.updatePageData();
  }

  


  
  updatePageData = () => {
    getAllEmployees(this.state.page,this.state.rowsPerPage).then(({data})=>{
        this.setState({
            userList:[...data.content],
            totalEmployee:data.totalElements
        })
    });
  };
  
  handleSubmit=()=>{
    getAllEmployeesBySearch(this.state.title,this.state.page,this.state.rowsPerPage)
    .then(({ data }) => {
      this.setState({
          userList: [...data.content],
          totalEmployee:data.totalElements
        })
      });
    //getAllEmployeesBySearch(this.state.title,this.state.page,this.state.rowsPerPage).then(({data}) => this.setState({userList: [...data],totalEmployee:data.totalElements}));
  }

  
  handleChange=(event)=>{
    this.setState({
      title: event.target.value
    });
    console.log(event.target.value);
  }


 handleClickExportBy=(key)=>{
  const fileName="employee";
  const fileType = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8';
  const fileExtension = '.xlsx';
  getAllEmployeesBySearchExcell(key).
    then(({data}) =>{
      const ws = XLSX.utils.json_to_sheet(data);
      const wb = { Sheets: { 'data': ws }, SheetNames: ['data'] };
      const excelBuffer = XLSX.write(wb, { bookType: 'xlsx', type: 'array' });
      const data2 = new Blob([excelBuffer], {type: fileType});
      FileSaver.saveAs(data2, fileName + fileExtension);
    });
  
}
  handleClickExportAll=()=>{
    const fileName="employee";
    const fileType = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8';
    const fileExtension = '.xlsx';
    getList().then(({ data }) => {
      const ws = XLSX.utils.json_to_sheet(data);
      const wb = { Sheets: { 'data': ws }, SheetNames: ['data'] };
      const excelBuffer = XLSX.write(wb, { bookType: 'xlsx', type: 'array' });
      const data1 = new Blob([excelBuffer], {type: fileType});
      FileSaver.saveAs(data1, fileName + fileExtension);
    });
  }

  render() {
    let {
      rowsPerPage,
      page,
      userList,
      shouldOpenConfirmationDialog,
      shouldOpenEditorDialog,

      title,
      totalEmployee
    } = this.state;

    
    const styleSearchGrid ={
      display:"flex",
    };

    const styleButton={
      height:"40px", 
      
    };
    const styleText={
      height:"40px", 
      width:"60px" ,
      marginLeft:"10px",
      marginTop:"3px",
    };

    var t={
      display:"none"
    }
    if(title == " "){
      var displaySearch=t;
    }
  
    

    return (
      <div className="m-sm-30">
        <div  className="mb-sm-30">
          <Breadcrumb routeSegments={[{ name: "Employee Table" }]} />
        </div>
        
        <Grid className="mb-16" container spacing={2} >
          <Grid item sm={6} xs={12} style={styleSearchGrid} >
            <Button
              style={styleButton}
              className="mb-16"
              variant="contained"
              color="primary"
              onClick={() => this.setState({uid:null,shouldOpenEditorDialog: true})}
            >
              Thêm Nhân Viên
            </Button>
                <TextField
                    style={styleText}
                    className="bg-paper flex-grow-1 mr-16"
                    margin="none"
                    name="title"
                    placeholder="Search here..."
                    value={this.state.title}
                    onChange={this.handleChange}
                    inputProps={{
                      style: {
                        padding: "10px"
                      }
                    }}
                    fullWidth
                ></TextField>
                <Button
                  style={styleButton}
                  className="mb-16"
                  variant="contained"
                  color="primary"
                  onClick={()=>{
                    this.state.page=0;
                    this.handleSubmit();
                  }}
                >
                  Tìm kiếm
                </Button>
                <Button
                  style={{height:"40px", 
                  position:"relative",
                  left:"500px"}}

                  className="mb-16"
                  variant="contained"
                  color="primary"
                  onClick={()=>{
                    if(title &&title !="" ){
                      this.handleClickExportBy(title)
                    }else{
                      this.handleClickExportAll();
                    }
                    }
                  }
                >
                  Xuất File
              </Button>  
          </Grid>
      
        </Grid>
        <p style={displaySearch}>Bạn đang tìm kiếm theo : {title}</p>
        <Card className="w-100 overflow-auto" elevation={10}>
          <Table className="crud-table" style={{ whiteSpace: "pre", minWidth: "850px" }}>
            <TableHead>
              <TableRow>
                <TableCell style={{witdh:"60px"}}>STT</TableCell>
                <TableCell>Mã nhân viên</TableCell>
                <TableCell>Họ Tên</TableCell>
                <TableCell style={{width:"200px"}}>Email</TableCell>
                <TableCell>Số điện thoại</TableCell>
                <TableCell>Tuổi</TableCell>
                <TableCell>Hành động</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              
              {userList
                .map((user, index) => (
                  <TableRow key={shortid.generate()}>
                    <TableCell className="px-0" align="left" style={{witdh:"10px"}}>
                    {user.stt}
                    </TableCell>
                    <TableCell className="px-0" 
                        style={{ textTransform: 'lowercase',
                       
                      }
                    }
                    >
                      {user.code}
                    </TableCell>
                    <TableCell className="px-0" align="left">
                      {user.name}
                    </TableCell>
                    
                    <TableCell className="px-1"  style={{ textTransform: 'lowercase', witdh:"200px",}}>
                      {user.email}
                    </TableCell>
                    <TableCell className="px-0" align="left">
                      {user.phone}
                    </TableCell>
                    <TableCell className="px-0" align="left">
                      {user.age}
                    </TableCell>
                    <TableCell className="px-0 border-none">
                      <IconButton
                        onClick={() =>
                          this.setState({
                            uid: user.id,
                            shouldOpenEditorDialog: true
                          })
                        }
                      >
                        <Icon color="primary">edit</Icon>
                      </IconButton>
                      <IconButton onClick={() => this.handleDeleteUser(user)}>
                        <Icon color="error">delete</Icon>
                      </IconButton>
                    </TableCell>
                  </TableRow>
                ))}
            </TableBody>
          </Table>

          <TablePagination
            className="px-16"
            rowsPerPageOptions={[5, 10, 25]}
            component="div"
            count={totalEmployee}
            rowsPerPage={rowsPerPage}
            page={page}
            backIconButtonProps={{
              "aria-label": "Previous Page"
            }}
            nextIconButtonProps={{
              "aria-label": "Next Page"
            }}
            onChangePage={this.handleChangePage}
            onChangeRowsPerPage={this.setRowsPerPage}
          />

          {shouldOpenEditorDialog && (
            <MemberEditorDialog
              handleClose={this.handleDialogClose}
              open={shouldOpenEditorDialog}
              uid={this.state.uid}
            />
          )}
          {shouldOpenConfirmationDialog && (
            <ConfirmationDialog
              open={shouldOpenConfirmationDialog}
              onConfirmDialogClose={this.handleDialogClose}
              onYesClick={this.handleConfirmationResponse}
              text="Bạn có muốn xóa nhân viên này?"
            />
          )}
        </Card>
        
      </div>
    );
  }
}

export default EmployeeTable;
