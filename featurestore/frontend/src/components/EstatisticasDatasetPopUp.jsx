import { useState, useEffect } from "react";
import { X } from "lucide-react";
import { Line } from "react-chartjs-2";
import { Chart as ChartJS, CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend } from "chart.js";
import { getAcessosDownloadsDataset, getHistoricoDataset } from "../services/relatoriosService";

ChartJS.register(CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend);

export default function EstatisticasDatasetPopUp({ aberto, onClose, idDataset }) {
    const [stats, setStats] = useState(null);
    const [historico, setHistorico] = useState([]);

    useEffect(() => {
        if (aberto && idDataset) {
            carregarDados();
        }
    }, [aberto, idDataset]);

    async function carregarDados() {
        try {
            const s = await getAcessosDownloadsDataset(idDataset);
            setStats(s);
            const h = await getHistoricoDataset(idDataset);
            setHistorico(h);
        } catch (e) {
            console.error(e.message);
        }
    }

    if (!aberto) return null;

    const chartData = {
        labels: historico.map(h => new Date(h.dia).toLocaleDateString("pt-BR")),
        datasets: [
            {
                label: "Acessos",
                data: historico.map(h => h.qntAcessos),
                borderColor: "#ef4b2d",
                backgroundColor: "rgba(239, 75, 45, 0.1)",
                tension: 0.3,
            },
            {
                label: "Downloads",
                data: historico.map(h => h.qntDownloads),
                borderColor: "#3cb5a0",
                backgroundColor: "rgba(60, 181, 160, 0.1)",
                tension: 0.3,
            },
        ],
    };

    const chartOptions = {
        responsive: true,
        plugins: {
            legend: { position: "top" },
        },
        scales: {
            y: { beginAtZero: true },
        },
    };

    return (
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/60 px-4">
            <div className="w-full max-w-2xl rounded bg-white px-8 py-6 shadow-lg">
                <div className="flex items-center justify-between mb-4">
                    <h2 className="text-lg font-bold text-black">Estatísticas Dataset</h2>
                    <button onClick={onClose} className="text-gray-500 hover:text-red-500">
                        <X size={20} />
                    </button>
                </div>

                <div className="border border-gray-300 rounded p-4">
                    <p className="text-sm mb-1">
                        <strong>Total de Visualizações:</strong> {stats?.qntAcessos ?? 0}
                    </p>
                    <p className="text-sm mb-3">
                        <strong>Total de Downloads:</strong> {stats?.qntDownloads ?? 0}
                    </p>

                    <p className="text-sm font-bold mb-2">Histórico de Visualização e Download:</p>

                    {historico.length > 0 ? (
                        <Line data={chartData} options={chartOptions} />
                    ) : (
                        <p className="text-xs text-gray-400">Nenhum histórico disponível</p>
                    )}
                </div>
            </div>
        </div>
    );
}