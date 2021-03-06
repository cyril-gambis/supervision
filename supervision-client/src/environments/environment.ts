// The file contents for the current environment will overwrite these during build.
// The build system defaults to the dev environment which uses `environment.ts`, but if you do
// `ng build --env=prod` then `environment.prod.ts` will be used instead.
// The list of which env maps to which file can be found in `.angular-cli.json`.

export const environment = {
  production: false,
  authorizationEndpoint: 'http://localhost:8091/api/v1.0',
  resourceEndpoint: 'http://localhost:8091/api/v1.0',
  dataEndpoint: 'http://localhost:8092/api/v1.0',
  environmentName: 'Development Environment'
};
