import history from "history.js";
import jwtAuthService from "../../services/jwtAuthService";
import ConstantList from "../../appConfig";
export const SET_USER_DATA = "USER_SET_DATA";
export const REMOVE_USER_DATA = "USER_REMOVE_DATA";
export const USER_LOGGED_OUT = "USER_LOGGED_OUT";

export function setUserData(user) {
  return dispatch => {
    jwtAuthService.loginWithToken()
    dispatch({
      type: SET_USER_DATA,
      data: user
    });
  };
}

export function logoutUser() {
  return dispatch => {
    jwtAuthService.logout();

    history.push({
      pathname: ConstantList.HOME_PAGE
      //pathname: ConstantList.ROOT_PATH+"session/signin"
    });

    dispatch({
      type: USER_LOGGED_OUT
    });
  };
}
