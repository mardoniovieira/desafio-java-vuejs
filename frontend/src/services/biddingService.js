import api from "./api";

export default {
  getByPage: (page) => api.get(`/bidding/${page}`),
  checkBidding: (bidding) => api.put(`/bidding/`, bidding),
};
