const  BASE_URL = process.env.REACT_APP_SERVER
const SIGN_IN = `${BASE_URL}/api/users/signin`
const SIGN_UP = `${BASE_URL}/api/users/create`
const CREATE_NEW_POST = `${BASE_URL}/api/posts/`
const FETCH_ALL_POSTS = `${BASE_URL}/api/posts/all`


const URL = {
    BASE_URL,
    SIGN_IN,
    SIGN_UP,
    CREATE_NEW_POST,
    FETCH_ALL_POSTS
};
export default URL
