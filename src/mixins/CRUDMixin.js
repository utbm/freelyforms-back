import Vue from "vue";
import { join } from "path";
import axios from "axios";

/**
 * @author TheRolf
 */
export default {
  methods: {
    /**
     * Computes target URL based on the information provided
     * @param {String} entityName Name of the entity concerned
     * @param {String?} id Value of the resource ID if any
     * @returns {String} REST corresponding URL
     */
    url: function (entityName, id) {
      if (Vue.ff.config.jsonserver) {
        return Vue.ff.config.baseURL + entityName + "/" + (id ? id : "");
      }

      const myURL = new URL(Vue.ff.config.baseURL);
      myURL.pathname = join(Vue.ff.config.database, entityName, id ? id : "");

      return myURL.toString();
    },
    /**
     * Creates data for given entity with corresponding data
     * @param {String} entityName Name of the entity concerned
     * @param {Object} data data pushed
     * @returns {Promise<import("axios").AxiosResponse>} result returned
     */
    ff_create: function (entityName, data) {
      return axios.post(this.url(entityName, undefined), data);
    },

    /**
     * Updates data for entity with given ID corresponding data
     * @param {String} entityName Name of the entity concerned
     * @param {String} id Id of the modified data
     * @param {Object} data data pushed
     * @returns {Promise<import("axios").AxiosResponse>} result returned
     */
    ff_update: function (entityName, id, data) {
      return axios.patch(this.url(entityName, id), data);
    },

    /**
     * Reads one or multiples entries
     * @param {String} entityName Name of the entity concerned
     * @param {String?} id Data ID wanted
     * @param {String[]?} columns Entry columns wanted
     * @param {any[]?} filters Filters if searching multiple entries
     * @returns {Promise<Object|Object[]>} data
     */
    ff_read: function (entityName, id, columns, filters) {
      const finalURL =
        this.url(entityName, id) +
        (id || Vue.ff.config.jsonserver
          ? ""
          : encodeURI(
              `?columns=${JSON.stringify(columns)}&filters=${JSON.stringify(
                filters
              )}`
            ));

      return axios.get(finalURL);
    },

    /**
     * Deletes entry with the given ID
     * @param {String} entityName Name of the entity concerned
     * @param {String?} id Value of the resource ID if any
     * @returns {Promise<import("axios").AxiosResponse>} result returned
     */
    ff_delete: function (entityName, id) {
      return axios.delete(this.url(entityName, id));
    },
  },
};
