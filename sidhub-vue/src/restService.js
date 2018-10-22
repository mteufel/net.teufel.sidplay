import axios from 'axios';

const baseUrl = '{{scheme}}://{{host}}{{port}}/{{appPath}}';
axios.defaults.baseURL = baseUrl;


const standardResponseObject = (entity, code, text, url) => ({
  entity,
  status: { code, text, url },
});

// if at any point axios will be replaced by something else,
// it will have to return the same responseObject
const getStandardResponseObject = response =>
  // creates standardResponseObj out of a axios "response" object
  standardResponseObject(
    response.data, response.request.status,
    response.request.statusText, response.request.responseURL,
  );

const logout = () => {
  // Logout if token ran out of date, maybe change in the future with Login-Overlay
  window.location.href = '#/logout';
};


const handleError = (error, reject, noLogout) => {
  console.log('ErrorConfig: ', error.config);

  if (error.response) {
    // The request was made and the server responded with a status code
    // that falls out of the range of 2xx
    console.log('ErrorResponse:', error.response);
    console.log(error.response.data);
    console.log(error.response.status);
    console.log(error.response.headers);

    if (noLogout !== true && error.response.status === 401) {
      logout();
      return; // implicit with logout
    }

    // all server responses that are not in the 200 range
    const standardResponseObj = getStandardResponseObject(error.response);
    console.log('2', reject);
    reject(standardResponseObj);
  } else if (error.request) {
    // The request was made but no response was received
    // `error.request` is an instance of XMLHttpRequest in the browser and an instance of
    // http.ClientRequest in node.js
    console.log('RequestError:', error.request);
    reject(standardResponseObject(null, 0, 'Axios RequestError', null));
  } else {
    // Something happened in setting up the request that triggered an Error
    console.log('Error', error.message);
    reject(standardResponseObject(null, -1, `Fatal Axios error=${error.message}`, null));
  }
};


// path is required
// params can be null
// opts is an optional object and can be used for logInfo, extensions and everyting else
const getRequest = (path, params, opts) => new Promise((resolve, reject) => {
  let credentials = false;
  if (opts.credentials) {
    credentials = [opts.credentials];
  }

  axios.request({
    method: 'get',
    url: path,
    headers: { Authorization: `Bearer ${JSON.parse(sessionStorage.getItem('login')).token}` },
    // `params` are the URL parameters to be sent with the request
    // Must be a plain object or a URLSearchParams object
    // e.g. {param1: value1, param2: value2} => {url} + {?param1=value1&param2=value2}
    params,
    withCredentials: credentials,
  }).then((response) => {
    if (opts != null && opts.log === true) {
      console.log('--- Axios LogInfo:', opts.logInfo, '---');
      console.log('Axios response', response);
      console.log(`Requested Path=${axios.defaults.baseURL}${path}`);
      console.log('Added Params=', params);
      console.log('Requested URL=', response.request.responseURL);
    }

    const standardResponseObj = getStandardResponseObject(response);

    resolve(standardResponseObj);
  }).catch((error) => {
    handleError(error, reject);
  });
});


export { getRequest, baseUrl, handleError, getStandardResponseObject };
