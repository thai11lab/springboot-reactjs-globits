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

import { getAllEmployees, deleteUser,getAllEmployeesBySearch,exportFileExcell1} from "./EmployeeService";
import MemberEditorDialog from "./MemberEditorDialog";
import { Breadcrumb, ConfirmationDialog } from "egret";
import shortid from "shortid";
import {ExportReactCSV} from './ExportReactCSV';

class EmployeeTable extends Component {
  state = {
    rowsPerPage: 10,
    page: 0,
    userList: [],
    shouldOpenEditorDialog: false,
    shouldOpenConfirmationDialog: false,
    title:"",
    totalEmployee:0
  };

  setPage = page => {
    this.setState({ page },function(){
      this.handleSubmit();
    });
  };

  setRowsPerPage = event => {
    this.setState({ rowsPerPage: event.target.value,page:0},function(){
      this.handleSubmit();
    });
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
    getAllEmployees().then(({ data }) => this.setState({ userList: [...data] }));
  };
  
  handleSubmit=()=>{
    getAllEmployeesBySearch(this.state.title,this.state.page,this.state.rowsPerPage).then(({ data }) => this.setState({ userList: [...data],totalEmployee:data.lenght}));
  }

  
  handleChange=(event)=>{
    this.setState({
      title: event.target.value
    });
    console.log(event.target.value);
  }
 handleClick=(title)=>{
    exportFileExcell1(title);
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
      width:"90px" ,
      marginLeft:"10px",
      marginTop:"3px",
    };

    const file="employee";
    

    return (
      <div className="m-sm-30">
        <div  className="mb-sm-30">
          <Breadcrumb routeSegments={[{ name: "Employee Table" }]} />
        </div>

        <Grid className="mb-16" container spacing={2}>
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
                  onClick={()=>this.handleSubmit()}
                >
                  Tìm kiếm
                </Button>
            <ExportReactCSV csvData={this.state.userList} fileName={file}></ExportReactCSV>
          </Grid>
        </Grid>

        <Card className="w-100 overflow-auto" elevation={6}>
          <Table className="crud-table" style={{ whiteSpace: "pre", minWidth: "750px" }}>
            <TableHead>
              <TableRow>
                <TableCell>STT</TableCell>
                <TableCell>Mã nhân viên</TableCell>
                <TableCell>Họ Tên</TableCell>
                <TableCell>Email</TableCell>
                <TableCell>Số điện thoại</TableCell>
                <TableCell>Tuổi</TableCell>
                <TableCell>Hành động</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {userList
                .map((user, index) => (
                  <TableRow key={shortid.generate()}>
                    <TableCell className="px-0" align="left">
                      {user.stt}
                    </TableCell>
                    <TableCell className="px-0">{user.code}</TableCell>
                    <TableCell className="px-0" align="left">
                      {user.name}
                    </TableCell>
                    
                    <TableCell className="px-0" align="left">
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
            /*count={this.state.totalEmployee}*/
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
