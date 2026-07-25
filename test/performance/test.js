import http from 'k6/http';
import { sleep, check } from 'k6';
import { LibraryApiAPIClient } from "./library-api.ts";

export const options = {
  vus: 10,
  duration: '30s',
};

const baseUrl = __ENV.BASE_URL || 'http://localhost:8080';
const libraryApiAPIClient = new LibraryApiAPIClient({ baseUrl });

export default function() {
  libraryApiAPIClient.postApiV1AccountsLogin({ username: 'admin12345', password: 'admin12345' })
  sleep(1);
}
