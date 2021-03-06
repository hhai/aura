import assert from 'assert';

export function assertTrue(condition, message) {
    assert(condition === true, message);
}

export function assertFalse(condition, message) {
    assert(condition === false, message);
}

export function assertUndefined(condition, message) {
    assert(condition === undefined, message);
}

export function assertDefined(condition, message) {
    assert(condition !== undefined, message);
}

export function assertNotUndefinedOrNull(condition, message) {
    assert(condition === undefined || condition === null, message);
}

export function assertStartsWith(start, full, message) {
    assert(full && full.indexOf && full.indexOf(start) === 0, message);
}