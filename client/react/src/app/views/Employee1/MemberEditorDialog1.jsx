import React, { Component } from "react";
import {
  Dialog,
  Button,
  Grid,
  FormControlLabel,
  Switch
} from "@material-ui/core";
import { ValidatorForm, TextValidator } from "react-material-ui-form-validator";
import { getUserById, updateUser,addNewUser } from "./EmployeeService1";
import { generateRandomId } from "utils";

//Validation form
// const notNumber =(value)=>{
//   if(Number.isNaN(value)){
//     return <small className="form-text text-danger">Họ tên không thể là số</small>;
//   } 
// }
class MemberEditorDialog extends Component {
  state = {
    code: "",
    name: "",
    email: "",
    phone: "",
    age: "",
  };

  handleChange = (event, source) => {
    event.persist();
    if (source === "switch") {
      this.setState({ isActive: event.target.checked });
      return;
    }

    this.setState({
      [event.target.name]: event.target.value
    });
  };

  handleFormSubmit = () => {
    let { id } = this.state;
    if (id) {
      updateUser({
        ...this.state
      }).then(() => {
        this.props.handleClose();
      });
    } else {
      addNewUser({
        id: generateRandomId(),
        ...this.state
      }).then(() => {
        this.props.handleClose();
      });
    }
  };

  componentWillMount() {
    getUserById(this.props.uid).then(data => this.setState({ ...data.data }));
  }



  

  render() {
    let {
      code,
      name,
      email,
      phone,
      age
    } = this.state;
    let { open, handleClose } = this.props;

    return (
      <Dialog onClose={handleClose} open={open}>
        <div className="p-24">
          <h4 className="mb-20">Cập nhật nhân viên</h4>
          <ValidatorForm ref="form" onSubmit={this.handleFormSubmit}>
            <Grid className="mb-16" container spacing={5}>
              <Grid item sm={6} xs={12}>
                <TextValidator
                  className="w-100 mb-16"
                  label="Mã nhân viên"
                  onChange={this.handleChange}
                  type="text"
                  name="code"
                  
                  value={code}
                  validators={["required"]}
                  errorMessages={["Mã nhân viên không được để trống !"]}
                />
                <TextValidator
                  className="w-100 mb-16"
                  label="Họ tên"
                  onChange={this.handleChange}
                  type="text"
                  name="name"
                  value={name}
                  validators={["required",'matchRegexp:[a-zA-Z] ']}
                  errorMessages={["Họ tên không được để trống !","Vui lòng nhập kí tự từ a-Z"]}
                />

                <TextValidator
                  className="w-100 mb-16"
                  label="Email"
                  onChange={this.handleChange}
                  type="email"
                  name="email"
                  value={email}
                  validators={["required","isEmail"]}
                  errorMessages={["Email không được để trống !","Vui lòng nhập đúng format aa@gmail.com"]}
                />

                <TextValidator
                  className="w-100 mb-16"
                  label="Số điện thoại"
                  onChange={this.handleChange}
                  type="text"
                  name="phone"
                  value={phone}
                  validators={["required",'matchRegexp:[0-9]']}
                  //,"Số điện thoại phải >=9 và <=11","Vui lòng nhập kí tự từ 0-9","Số điện thoại phải >=9 và <=11"
                  errorMessages={["Số điện thoai không được để trống !","Vui lòng nhập kí tự từ 0-9"]}
                />
                <TextValidator
                  className="w-100 mb-16"
                  label="Age"
                  onChange={this.handleChange}
                  type="number"
                  name="age"
                  value={age}
                  validators={["required",'minNumber:0','maxNumber:130']}
                  errorMessages={["Tuổi không được để trống !","Tuổi không được mang kí hiệu -","Tuổi không thể vượt quá 130"]}
                />
                
              </Grid>
            </Grid>

            <div className="flex flex-space-between flex-middle">
              <Button variant="contained" color="primary" type="submit">
                Save
              </Button>
              <Button onClick={() => this.props.handleClose()}>Cancel</Button>
            </div>
          </ValidatorForm>
        </div>
      </Dialog>
    );
  }
}

export default MemberEditorDialog;
