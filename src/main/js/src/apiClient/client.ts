/* eslint-disable */
export namespace Schemas {
	// <Schemas>
	export type TypeRule = Partial<{
		associatedTypes: Array<"INTEGER" | "STRING" | "FLOAT" | "DATE" | "DATETIME" | "BOOLEAN" | "SELECTOR">;
		name: string;
		value: string;
	}>;
	export type Rule = Partial<{
		excludes: Array<string>;
		fieldType: "INTEGER" | "STRING" | "FLOAT" | "DATE" | "DATETIME" | "BOOLEAN" | "SELECTOR";
		hidden: boolean;
		optional: boolean;
		selectorValues: Array<string>;
		typeRules: Array<TypeRule>;
	}>;
	export type Field = Partial<{ caption: string; label: string; name: string; rules: Rule }>;
	export type ObjectId = Partial<{ date: string; timestamp: number }>;
	export type Group = Partial<{ caption: string; fields: Array<Field>; label: string; name: string }>;
	export type FormData = Partial<{ _id: ObjectId; groups: Array<Group>; prefabName: string }>;
	export type View = Partial<{ contentType: string }>;
	export type ModelAndView = Partial<{
		empty: boolean;
		model: Partial<{}>;
		modelMap: unknown;
		reference: boolean;
		status:
			| "100 CONTINUE"
			| "101 SWITCHING_PROTOCOLS"
			| "102 PROCESSING"
			| "103 CHECKPOINT"
			| "200 OK"
			| "201 CREATED"
			| "202 ACCEPTED"
			| "203 NON_AUTHORITATIVE_INFORMATION"
			| "204 NO_CONTENT"
			| "205 RESET_CONTENT"
			| "206 PARTIAL_CONTENT"
			| "207 MULTI_STATUS"
			| "208 ALREADY_REPORTED"
			| "226 IM_USED"
			| "300 MULTIPLE_CHOICES"
			| "301 MOVED_PERMANENTLY"
			| "302 FOUND"
			| "302 MOVED_TEMPORARILY"
			| "303 SEE_OTHER"
			| "304 NOT_MODIFIED"
			| "305 USE_PROXY"
			| "307 TEMPORARY_REDIRECT"
			| "308 PERMANENT_REDIRECT"
			| "400 BAD_REQUEST"
			| "401 UNAUTHORIZED"
			| "402 PAYMENT_REQUIRED"
			| "403 FORBIDDEN"
			| "404 NOT_FOUND"
			| "405 METHOD_NOT_ALLOWED"
			| "406 NOT_ACCEPTABLE"
			| "407 PROXY_AUTHENTICATION_REQUIRED"
			| "408 REQUEST_TIMEOUT"
			| "409 CONFLICT"
			| "410 GONE"
			| "411 LENGTH_REQUIRED"
			| "412 PRECONDITION_FAILED"
			| "413 PAYLOAD_TOO_LARGE"
			| "413 REQUEST_ENTITY_TOO_LARGE"
			| "414 URI_TOO_LONG"
			| "414 REQUEST_URI_TOO_LONG"
			| "415 UNSUPPORTED_MEDIA_TYPE"
			| "416 REQUESTED_RANGE_NOT_SATISFIABLE"
			| "417 EXPECTATION_FAILED"
			| "418 I_AM_A_TEAPOT"
			| "419 INSUFFICIENT_SPACE_ON_RESOURCE"
			| "420 METHOD_FAILURE"
			| "421 DESTINATION_LOCKED"
			| "422 UNPROCESSABLE_ENTITY"
			| "423 LOCKED"
			| "424 FAILED_DEPENDENCY"
			| "425 TOO_EARLY"
			| "426 UPGRADE_REQUIRED"
			| "428 PRECONDITION_REQUIRED"
			| "429 TOO_MANY_REQUESTS"
			| "431 REQUEST_HEADER_FIELDS_TOO_LARGE"
			| "451 UNAVAILABLE_FOR_LEGAL_REASONS"
			| "500 INTERNAL_SERVER_ERROR"
			| "501 NOT_IMPLEMENTED"
			| "502 BAD_GATEWAY"
			| "503 SERVICE_UNAVAILABLE"
			| "504 GATEWAY_TIMEOUT"
			| "505 HTTP_VERSION_NOT_SUPPORTED"
			| "506 VARIANT_ALSO_NEGOTIATES"
			| "507 INSUFFICIENT_STORAGE"
			| "508 LOOP_DETECTED"
			| "509 BANDWIDTH_LIMIT_EXCEEDED"
			| "510 NOT_EXTENDED"
			| "511 NETWORK_AUTHENTICATION_REQUIRED";
		view: View;
		viewName: string;
	}>;
	export type Optional_User_ = Partial<{ empty: boolean; present: boolean }>;
	export type Prefab = Partial<{ _id: ObjectId; caption: string; groups: Array<Group>; label: string; name: string }>;
	export type User = Partial<{ age: number; firstname: string; id: string; lastname: string }>;

	// </Schemas>
}

export namespace Endpoints {
	// <Endpoints>

	export type get_GetAllFormDataUsingGET = {
		method: "GET";
		path: "/api/formdata";
		parameters: never;
		response: unknown;
	};
	export type get_GetAllFormDataFromPrefabUsingGET = {
		method: "GET";
		path: "/api/formdata/{prefab}";
		parameters: {
			path: { prefab: string };
		};
		response: unknown;
	};
	export type put_PatchFormDataUsingPUT = {
		method: "PUT";
		path: "/api/formdata/{prefab}";
		parameters: {
			body: FormData;
		};
		response: unknown;
	};
	export type post_PostFormDataUsingPOST = {
		method: "POST";
		path: "/api/formdata/{prefab}";
		parameters: {
			body: FormData;
		};
		response: unknown;
	};
	export type delete_DeleteFormDataUsingDELETE = {
		method: "DELETE";
		path: "/api/formdata/{prefab}";
		parameters: {
			body: FormData;
		};
		response: unknown;
	};
	export type patch_PatchFormDataUsingPATCH = {
		method: "PATCH";
		path: "/api/formdata/{prefab}";
		parameters: {
			body: FormData;
		};
		response: unknown;
	};
	export type get_GetAllFormDataFromPrefabFieldUsingGET = {
		method: "GET";
		path: "/api/formdata/{prefab}/{group}/{field}";
		parameters: {
			path: { prefab: string; group: string; field: string };
		};
		response: unknown;
	};
	export type get_GetAllPrefabsUsingGET = {
		method: "GET";
		path: "/api/prefabs";
		parameters: never;
		response: unknown;
	};
	export type post_PostPrefabUsingPOST = {
		method: "POST";
		path: "/api/prefabs";
		parameters: {
			body: Schemas.Prefab;
		};
		response: unknown;
	};
	export type get_GetPrefabUsingGET = {
		method: "GET";
		path: "/api/prefabs/{name}";
		parameters: {
			path: { name: string };
		};
		response: unknown;
	};
	export type put_PatchPrefabUsingPUT = {
		method: "PUT";
		path: "/api/prefabs/{name}";
		parameters: {
			path: { name: string };

			body: Schemas.Prefab;
		};
		response: unknown;
	};
	export type delete_DeletePrefabUsingDELETE = {
		method: "DELETE";
		path: "/api/prefabs/{name}";
		parameters: {
			path: { name: string };
		};
		response: unknown;
	};
	export type patch_PatchPrefabUsingPATCH = {
		method: "PATCH";
		path: "/api/prefabs/{name}";
		parameters: {
			path: { name: string };

			body: Schemas.Prefab;
		};
		response: unknown;
	};
	export type post_SaveUserUsingPOST = {
		method: "POST";
		path: "/api/users";
		parameters: {
			body: Schemas.User;
		};
		response: unknown;
	};
	export type get_GetUserUsingGET = {
		method: "GET";
		path: "/api/users/{id}";
		parameters: {
			path: { id: string };
		};
		response: unknown;
	};
	export type delete_DeleteUserUsingDELETE = {
		method: "DELETE";
		path: "/api/users/{id}";
		parameters: {
			path: { id: string };
		};
		response: unknown;
	};
	export type get_ErrorHtmlUsingGET = {
		method: "GET";
		path: "/error";
		parameters: never;
		response: unknown;
	};
	export type put_ErrorHtmlUsingPUT = {
		method: "PUT";
		path: "/error";
		parameters: never;
		response: unknown;
	};
	export type post_ErrorHtmlUsingPOST = {
		method: "POST";
		path: "/error";
		parameters: never;
		response: unknown;
	};
	export type delete_ErrorHtmlUsingDELETE = {
		method: "DELETE";
		path: "/error";
		parameters: never;
		response: unknown;
	};
	export type options_ErrorHtmlUsingOPTIONS = {
		method: "OPTIONS";
		path: "/error";
		parameters: never;
		response: unknown;
	};
	export type head_ErrorHtmlUsingHEAD = {
		method: "HEAD";
		path: "/error";
		parameters: never;
		response: unknown;
	};
	export type patch_ErrorHtmlUsingPATCH = {
		method: "PATCH";
		path: "/error";
		parameters: never;
		response: unknown;
	};

	// </Endpoints>
}

// <EndpointByMethod>
export type EndpointByMethod = {
	get: {
		"/api/formdata": Endpoints.get_GetAllFormDataUsingGET;
		"/api/formdata/{prefab}": Endpoints.get_GetAllFormDataFromPrefabUsingGET;
		"/api/formdata/{prefab}/{group}/{field}": Endpoints.get_GetAllFormDataFromPrefabFieldUsingGET;
		"/api/prefabs": Endpoints.get_GetAllPrefabsUsingGET;
		"/api/prefabs/{name}": Endpoints.get_GetPrefabUsingGET;
		"/api/users/{id}": Endpoints.get_GetUserUsingGET;
		"/error": Endpoints.get_ErrorHtmlUsingGET;
	};
	put: {
		"/api/formdata/{prefab}": Endpoints.put_PatchFormDataUsingPUT;
		"/api/prefabs/{name}": Endpoints.put_PatchPrefabUsingPUT;
		"/error": Endpoints.put_ErrorHtmlUsingPUT;
	};
	post: {
		"/api/formdata/{prefab}": Endpoints.post_PostFormDataUsingPOST;
		"/api/prefabs": Endpoints.post_PostPrefabUsingPOST;
		"/api/users": Endpoints.post_SaveUserUsingPOST;
		"/error": Endpoints.post_ErrorHtmlUsingPOST;
	};
	delete: {
		"/api/formdata/{prefab}": Endpoints.delete_DeleteFormDataUsingDELETE;
		"/api/prefabs/{name}": Endpoints.delete_DeletePrefabUsingDELETE;
		"/api/users/{id}": Endpoints.delete_DeleteUserUsingDELETE;
		"/error": Endpoints.delete_ErrorHtmlUsingDELETE;
	};
	patch: {
		"/api/formdata/{prefab}": Endpoints.patch_PatchFormDataUsingPATCH;
		"/api/prefabs/{name}": Endpoints.patch_PatchPrefabUsingPATCH;
		"/error": Endpoints.patch_ErrorHtmlUsingPATCH;
	};
	options: {
		"/error": Endpoints.options_ErrorHtmlUsingOPTIONS;
	};
	head: {
		"/error": Endpoints.head_ErrorHtmlUsingHEAD;
	};
};

// </EndpointByMethod>

// <EndpointByMethod.Shorthands>
export type GetEndpoints = EndpointByMethod["get"];
export type PutEndpoints = EndpointByMethod["put"];
export type PostEndpoints = EndpointByMethod["post"];
export type DeleteEndpoints = EndpointByMethod["delete"];
export type PatchEndpoints = EndpointByMethod["patch"];
export type OptionsEndpoints = EndpointByMethod["options"];
export type HeadEndpoints = EndpointByMethod["head"];
export type AllEndpoints = EndpointByMethod[keyof EndpointByMethod];
// </EndpointByMethod.Shorthands>

// <ApiClientTypes>
export type EndpointParameters = {
	body?: unknown;
	query?: Record<string, unknown>;
	header?: Record<string, unknown>;
	path?: Record<string, unknown>;
};

export type MutationMethod = "post" | "put" | "patch" | "delete";
export type Method = "get" | "head" | "options" | MutationMethod;

export type DefaultEndpoint = {
	parameters?: EndpointParameters | undefined;
	response: unknown;
};

export type Endpoint<TConfig extends DefaultEndpoint = DefaultEndpoint> = {
	operationId: string;
	method: Method;
	path: string;
	parameters?: TConfig["parameters"];
	meta: {
		alias: string;
		hasParameters: boolean;
		areParametersRequired: boolean;
	};
	response: TConfig["response"];
};

type Fetcher = (
	method: Method,
	url: string,
	parameters?: EndpointParameters | undefined,
) => Promise<Endpoint["response"]>;

type RequiredKeys<T> = {
	[P in keyof T]-?: undefined extends T[P] ? never : P;
}[keyof T];

type MaybeOptionalArg<T> = RequiredKeys<T> extends never ? [config?: T] : [config: T];

// </ApiClientTypes>

// <ApiClient>
export class ApiClient {
	baseUrl: string = "";

	constructor(public fetcher: Fetcher) {}

	setBaseUrl(baseUrl: string) {
		this.baseUrl = baseUrl;
		return this;
	}

	// <ApiClient.get>
	get<Path extends keyof GetEndpoints, TEndpoint extends GetEndpoints[Path]>(
		path: Path,
		...params: MaybeOptionalArg<TEndpoint["parameters"]>
	): Promise<TEndpoint["response"]> {
		return this.fetcher("get", this.baseUrl + path, params[0]);
	}
	// </ApiClient.get>

	// <ApiClient.put>
	put<Path extends keyof PutEndpoints, TEndpoint extends PutEndpoints[Path]>(
		path: Path,
		...params: MaybeOptionalArg<TEndpoint["parameters"]>
	): Promise<TEndpoint["response"]> {
		return this.fetcher("put", this.baseUrl + path, params[0]);
	}
	// </ApiClient.put>

	// <ApiClient.post>
	post<Path extends keyof PostEndpoints, TEndpoint extends PostEndpoints[Path]>(
		path: Path,
		...params: MaybeOptionalArg<TEndpoint["parameters"]>
	): Promise<TEndpoint["response"]> {
		return this.fetcher("post", this.baseUrl + path, params[0]);
	}
	// </ApiClient.post>

	// <ApiClient.delete>
	delete<Path extends keyof DeleteEndpoints, TEndpoint extends DeleteEndpoints[Path]>(
		path: Path,
		...params: MaybeOptionalArg<TEndpoint["parameters"]>
	): Promise<TEndpoint["response"]> {
		return this.fetcher("delete", this.baseUrl + path, params[0]);
	}
	// </ApiClient.delete>

	// <ApiClient.patch>
	patch<Path extends keyof PatchEndpoints, TEndpoint extends PatchEndpoints[Path]>(
		path: Path,
		...params: MaybeOptionalArg<TEndpoint["parameters"]>
	): Promise<TEndpoint["response"]> {
		return this.fetcher("patch", this.baseUrl + path, params[0]);
	}
	// </ApiClient.patch>

	// <ApiClient.options>
	options<Path extends keyof OptionsEndpoints, TEndpoint extends OptionsEndpoints[Path]>(
		path: Path,
		...params: MaybeOptionalArg<TEndpoint["parameters"]>
	): Promise<TEndpoint["response"]> {
		return this.fetcher("options", this.baseUrl + path, params[0]);
	}
	// </ApiClient.options>

	// <ApiClient.head>
	head<Path extends keyof HeadEndpoints, TEndpoint extends HeadEndpoints[Path]>(
		path: Path,
		...params: MaybeOptionalArg<TEndpoint["parameters"]>
	): Promise<TEndpoint["response"]> {
		return this.fetcher("head", this.baseUrl + path, params[0]);
	}
	// </ApiClient.head>
}

export function createApiClient(fetcher: Fetcher, baseUrl?: string) {
	return new ApiClient(fetcher).setBaseUrl(baseUrl ?? "");
}

/**
 Example usage:
 const api = createApiClient((method, url, params) =>
   fetch(url, { method, body: JSON.stringify(params) }).then((res) => res.json()),
 );
 api.get("/users").then((users) => console.log(users));
 api.post("/users", { body: { name: "John" } }).then((user) => console.log(user));
 api.put("/users/:id", { path: { id: 1 }, body: { name: "John" } }).then((user) => console.log(user));
*/

// </ApiClient
