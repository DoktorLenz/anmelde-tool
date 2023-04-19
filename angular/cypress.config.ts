/* eslint-disable @typescript-eslint/no-var-requires */
/* eslint-disable import/no-extraneous-dependencies */
import { defineConfig } from 'cypress';
const browserify = require('@cypress/browserify-preprocessor');
const cucumber = require('cypress-cucumber-preprocessor').default;
const resolve = require('resolve');
const fs = require('fs-extra');
const path = require('path');

function getConfigurationByFile(file: string) {
  const pathToConfigFile = path.resolve('', 'cypress/config', `${file}.json`);
  return fs.readJson(pathToConfigFile);
}

export default defineConfig({
  projectId: 'crabrc',
  component: {
    devServer: {
      framework: 'angular',
      bundler: 'webpack',
    },
    specPattern: '**/*.cy.ts',
  },

  e2e: {
    setupNodeEvents(on, config) {
      const options = {
        ...browserify.defaultOptions,
        typescript: resolve.sync('typescript', { baseDir: config.projectRoot }),
      };

      on('file:preprocessor', cucumber(options));

      const file = config.env['configFile'] || 'dev';
      return getConfigurationByFile(file);

    },
    specPattern: 'cypress/**/*.{feature,features}',
  },
});
