export function formatDate(dateString: string | undefined): string {
    if (!dateString) return '';

    return new Date(dateString).toLocaleDateString(navigator.language, {
        day: '2-digit',
        month: '2-digit',
        year: 'numeric',
        hour: '2-digit',
        minute: '2-digit',
        second: '2-digit'
    });
}