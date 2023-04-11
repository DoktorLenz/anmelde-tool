/* eslint-disable import/no-extraneous-dependencies */
/* eslint-disable @typescript-eslint/no-var-requires */
const setEnv = () => {
  const fs = require('fs');
  const writeFile = fs.writeFile;
  const targetPath = './src/environments/environment.ts';
  const colors = require('colors');
  const appVersion = require('../../package.json').version;
  require('dotenv').config({
    path: 'src/environments/.env',
  });

  const envConfigFile = `export const environment = {
    appVersion: '${appVersion}',
    baseUrl: '${process.env['ANMELDE_TOOL_BASE_URL']}',
    prduction: ${process.env['ANMELDE_TOOL_PRODUCTION'] === 'true'},
  };`;

  console.log(colors.magenta('The file `environment.ts` will be written with the following content: \n'));

  writeFile(targetPath, envConfigFile, (err: any) => {
    if (err) {
      console.error(err);
      throw err;
    } else {
      console.log(colors.magenta(`Angular environment.ts file generated correctly at ${targetPath} \n`));
    }
  });
};

setEnv();
