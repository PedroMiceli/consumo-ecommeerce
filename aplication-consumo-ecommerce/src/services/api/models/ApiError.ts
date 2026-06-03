export class ApiError extends Error {
    constructor(
        message: string,
        status: number,
        details?: unknown
    ) {
        super(message);
        this.name = 'ApiError';
    }
}
